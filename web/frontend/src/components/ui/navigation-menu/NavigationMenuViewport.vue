<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import { type HTMLAttributes, computed } from "vue"
	import {
		NavigationMenuViewport,
		type NavigationMenuViewportProps,
		useForwardProps,
	} from "radix-vue"
	import { cn } from "@/lib/utils"

	const props = defineProps<NavigationMenuViewportProps & { class?: HTMLAttributes["class"] }>()

	const delegatedProps = computed(() => {
		const { class: _, ...delegated } = props

		return delegated
	})

	const forwardedProps = useForwardProps(delegatedProps)
</script>

<template>
	<div class="absolute left-0 top-full flex justify-center">
		<NavigationMenuViewport
			:class="
        cn(
          'origin-top-center relative mt-1.5 h-[--radix-navigation-menu-viewport-height] w-full overflow-hidden rounded-md border bg-popover text-popover-foreground shadow-lg data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-90 md:w-[--radix-navigation-menu-viewport-width]',
          props.class,
        )
      "
			v-bind="forwardedProps"
		/>
	</div>
</template>
