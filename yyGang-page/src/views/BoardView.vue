<template>
    <main>
        <h2>자유 게시판</h2>

        <div id="mainArea">
            <BoardTable :boards="boards" @item-click="itemClick"/>
            
            <RouterLink class="nav-link" :to="{name:`board/write`}"><button type="button" class="btn btn-success">등록하기</button></RouterLink>
        </div>
        

    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import BoardTable from '@/components/board/BoardTable.vue';
    import { onMounted, ref } from 'vue'; 
    import { useRouter } from 'vue-router';

    const router = useRouter();
    
    const boards = ref([]);
    const fetchBoards = async()=>{
        try {
            const response = await apiClient.get(`/board`);

            boards.value = response.data;
            
        } catch (error) {
            
        }
    };

    const itemClick = (id)=>{
        router.push({name:'board/id', params:{id}});
    }
    


    onMounted(function(){
        fetchBoards();
    })

    
</script>

<style>
    #mainArea{
        margin-top: 5%;
        height: 70%;
    }   
</style>