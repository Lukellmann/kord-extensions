<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import { type HTMLAttributes, computed } from "vue"
	import {
		TooltipContent,
		type TooltipContentEmits,
		type TooltipContentProps,
		TooltipPortal,
		useForwardPropsEmits,
	} from "radix-vue"
	import { cn } from "@/lib/utils"

	defineOptions({
		inheritAttrs: false,
	})

	const props = withDefaults(defineProps<TooltipContentProps & { class?: HTMLAttributes["class"] }>(), {
		sideOffset: 4,
	})

	const emits = defineEmits<TooltipContentEmits>()

	const delegatedProps = computed(() => {
		const { class: _, ...delegated } = props

		return delegated
	})

	const forwarded = useForwardPropsEmits(delegatedProps, emits)
</script>

<template>
	<TooltipPortal>
		<TooltipContent :class="cn('z-50 overflow-hidden rounded-md border bg-popover px-3 py-1.5 text-sm text-popover-foreground shadow-md animate-in fade-in-0 zoom-in-95 data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=closed]:zoom-out-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2', props.class)"
										v-bind="{ ...forwarded, ...$attrs }">
			<slot />
		</TooltipContent>
	</TooltipPortal>
</template>
