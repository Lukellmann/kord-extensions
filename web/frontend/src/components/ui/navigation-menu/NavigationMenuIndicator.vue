<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import { type HTMLAttributes, computed } from "vue"
	import { NavigationMenuIndicator, type NavigationMenuIndicatorProps, useForwardProps } from "radix-vue"
	import { cn } from "@/lib/utils"

	const props = defineProps<NavigationMenuIndicatorProps & { class?: HTMLAttributes["class"] }>()

	const delegatedProps = computed(() => {
		const { class: _, ...delegated } = props

		return delegated
	})

	const forwardedProps = useForwardProps(delegatedProps)
</script>

<template>
	<NavigationMenuIndicator
		:class="cn('top-full z-[1] flex h-1.5 items-end justify-center overflow-hidden data-[state=visible]:animate-in data-[state=hidden]:animate-out data-[state=hidden]:fade-out data-[state=visible]:fade-in', props.class)"
		v-bind="forwardedProps"
	>
		<div class="relative top-[60%] h-2 w-2 rotate-45 rounded-tl-sm bg-border shadow-md" />
	</NavigationMenuIndicator>
</template>
