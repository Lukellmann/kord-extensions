<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import { type HTMLAttributes, computed } from "vue"
	import { ToastClose, type ToastCloseProps } from "radix-vue"
	import { X } from "lucide-vue-next"
	import { cn } from "@/lib/utils"

	const props = defineProps<ToastCloseProps & {
		class?: HTMLAttributes["class"]
	}>()

	const delegatedProps = computed(() => {
		const { class: _, ...delegated } = props

		return delegated
	})
</script>

<template>
	<ToastClose :class="cn('absolute right-2 top-2 rounded-md p-1 text-foreground/50 opacity-0 transition-opacity hover:text-foreground focus:opacity-100 focus:outline-none focus:ring-2 group-hover:opacity-100 group-[.destructive]:text-red-300 group-[.destructive]:hover:text-red-50 group-[.destructive]:focus:ring-red-400 group-[.destructive]:focus:ring-offset-red-600', props.class)"
							v-bind="delegatedProps">
		<X class="h-4 w-4" />
	</ToastClose>
</template>
