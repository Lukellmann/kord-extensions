<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script generic="T extends Record<string, any>" lang="ts" setup>
	import { VisDonut, VisSingleContainer } from "@unovis/vue"
	import { Donut } from "@unovis/ts"
	import { type Component, computed, ref } from "vue"
	import { useMounted } from "@vueuse/core"
	import type { BaseChartProps } from "."
	import { ChartSingleTooltip, defaultColors } from "@/components/ui/chart"
	import { cn } from "@/lib/utils"

	const props = withDefaults(defineProps<Pick<BaseChartProps<T>, "data" | "colors" | "index" | "margin" | "showLegend" | "showTooltip" | "filterOpacity"> & {
		/**
		 * Sets the name of the key containing the quantitative chart values.
		 */
		category: KeyOfT
		/**
		 * Change the type of the chart
		 * @default "donut"
		 */
		type?: "donut" | "pie"
		/**
		 * Function to sort the segment
		 */
		sortFunction?: (a: any, b: any) => number | undefined
		/**
		 * Controls the formatting for the label.
		 */
		valueFormatter?: (tick: number, i?: number, ticks?: number[]) => string
		/**
		 * Render custom tooltip component.
		 */
		customTooltip?: Component
	}>(), {
		margin: () => ({ top: 0, bottom: 0, left: 0, right: 0 }),
		sortFunction: () => undefined,
		valueFormatter: (tick: number) => `${tick}`,
		type: "donut",
		filterOpacity: 0.2,
		showTooltip: true,
		showLegend: true,
	})

	type KeyOfT = Extract<keyof T, string>
	type Data = typeof props.data[number]

	const category = computed(() => props.category as KeyOfT)
	const index = computed(() => props.index as KeyOfT)

	const isMounted = useMounted()
	const activeSegmentKey = ref<string>()
	const colors = computed(() => props.colors?.length ? props.colors : defaultColors(props.data.filter(d => d[props.category]).filter(Boolean).length))
	const legendItems = computed(() => props.data.map((item, i) => ({
		name: item[props.index],
		color: colors.value[i],
		inactive: false,
	})))

	const totalValue = computed(() => props.data.reduce((prev, curr) => {
		return prev + curr[props.category]
	}, 0))
</script>

<template>
	<div :class="cn('w-full h-48 flex flex-col items-end', $attrs.class ?? '')">
		<VisSingleContainer :data="data" :margin="{ left: 20, right: 20 }" :style="{ height: isMounted ? '100%' : 'auto' }">
			<ChartSingleTooltip
				:custom-tooltip="customTooltip"
				:index="category"
				:items="legendItems"
				:selector="Donut.selectors.segment"
				:value-formatter="valueFormatter"
			/>

			<VisDonut
				:arc-width="type === 'donut' ? 20 : 0"
				:central-label="type === 'donut' ? valueFormatter(totalValue) : ''"
				:color="colors"
				:events="{
          [Donut.selectors.segment]: {
            click: (d: Data, ev: PointerEvent, i: number, elements: HTMLElement[]) => {
              if (d?.data?.[index] === activeSegmentKey) {
                activeSegmentKey = undefined
                elements.forEach(el => el.style.opacity = '1')
              }
              else {
                activeSegmentKey = d?.data?.[index]
                elements.forEach(el => el.style.opacity = `${filterOpacity}`)
                elements[i].style.opacity = '1'
              }
            },
          },
        }"
				:show-background="false"
				:sort-function="sortFunction"
				:value="(d: Data) => d[category]"
			/>

			<slot />
		</VisSingleContainer>
	</div>
</template>
