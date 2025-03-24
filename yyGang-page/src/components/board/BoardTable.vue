<template>
    <table class="table">
    <thead>
        <tr>
        <th scope="col">번호</th>
        <th scope="col">제목</th>
        <th scope="col">작성자</th>
        <th scope="col">등록일</th>
        </tr>
    </thead>
    <tbody class="table-group-divider">
        <tr v-for="board in boards" :key="board.id" @click.stop="emit('item-click', board.id)">
            <th scope="row">{{ board.id }}</th>
            <td>{{ board.title }}</td>
            <td>{{ board.userName }}</td>
            <td>{{ board.createdAt }}</td>
        </tr>
    </tbody>
    </table>
</template>

<script setup>

    const props = defineProps({
        boards:{
            type: Array,
            // required:true
        }
    });

    const emit = defineEmits(['item-click'])


</script>