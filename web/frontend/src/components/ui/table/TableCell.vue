<!--
	This Source Code Form is subject to the terms of the Mozilla Public
	License, v. 2.0. If a copy of the MPL was not distributed with this
	file, You can obtain one at https://mozilla.org/MPL/2.0/.
-->

<script lang="ts" setup>
	import type { HTMLAttributes } from "vue"
	import { cn } from "@/lib/utils"

	const props = defineProps<{
		class?: HTMLAttributes["class"]
	}>()
</script>

<template>
	<td
		:class="
      cn(
        'p-4 align-middle [&:has([role=checkbox])]:pr-0',
        props.class,
      )
    "
	>
		<slot />
	</td>
</template>
