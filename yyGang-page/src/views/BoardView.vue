<template>
    <main>
        <h2>자유 게시판</h2>

        <div id="mainArea">
            <BoardTable :boards="boards" @item-click="itemClick"/>
            
            <RouterLink class="nav-link" :to="{name:`board/write`}"><button type="button" class="btn btn-success">등록하기</button></RouterLink>

            <!-- <Pagination :pageInfo="pageInfo"
            @change-page="changePage"/> -->
        </div>
        

    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import BoardTable from '@/components/board/BoardTable.vue';
    import { onMounted, ref, reactive, watch } from 'vue'; 
    import { useRoute, useRouter } from 'vue-router';
    import Pagination from '@/components/board/Pagination.vue';

    const router = useRouter();
    const currentRoute = useRoute();

    // const pageInfo = reactive({
    //     // 값을 정수로 변환하고 실패하면 1을 기본값으로 사용
    //     currentPage: parseInt(currentRoute.query.page) || 1,
    //     totalCount: 0, // 전체 데이터 수
    //     pageLimit: 5, // 페이지네이션에 보이는 페이지의 수
    //     listLimit: 0 // 한 페이지의 표시될 리스트의 수 
    // });
    
    const boards = ref([]);
    const fetchBoards = async()=>{
        try {
            const response = await apiClient.get(`/board`);

            boards.value = response.data;
            // pageInfo.totalCount = response.data.totalCount;
            // pageInfo.listLimit = 10;
            
        } catch (error) {
            
        }
    };

    const itemClick = (id)=>{
        router.push({name:'board/id', params:{id}});
    };
    
    // const changePage = ({page,totalPages})=>{
    //    if(page >=1 && page <= totalPages){
    //         router.push({name:'board', query:{page}});
    //    }
    // };

    // watch(currentRoute, ()=>{
    //     pageInfo.currentPage = parseInt(currentRoute.query.page) || 1;

    //     fetchDepartments(pageInfo.currentPage);
    // });


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