<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import { ContextMenuTrigger, type ContextMenuTriggerProps, useForwardProps } from "radix-vue"

	const props = defineProps<ContextMenuTriggerProps>()

	const forwardedProps = useForwardProps(props)
</script>

<template>
	<ContextMenuTrigger v-bind="forwardedProps">
		<slot />
	</ContextMenuTrigger>
</template>
