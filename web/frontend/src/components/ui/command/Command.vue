<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import { type HTMLAttributes, computed } from "vue"
	import type { ComboboxRootEmits, ComboboxRootProps } from "radix-vue"
	import { ComboboxRoot, useForwardPropsEmits } from "radix-vue"
	import { cn } from "@/lib/utils"

	const props = withDefaults(defineProps<ComboboxRootProps & { class?: HTMLAttributes["class"] }>(), {
		open: true,
		modelValue: "",
	})

	const emits = defineEmits<ComboboxRootEmits>()

	const delegatedProps = computed(() => {
		const { class: _, ...delegated } = props

		return delegated
	})

	const forwarded = useForwardPropsEmits(delegatedProps, emits)
</script>

<template>
	<ComboboxRoot
		:class="cn('flex h-full w-full flex-col overflow-hidden rounded-md bg-popover text-popover-foreground', props.class)"
		v-bind="forwarded"
	>
		<slot />
	</ComboboxRoot>
</template>
