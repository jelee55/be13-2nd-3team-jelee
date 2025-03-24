<template>
    <main>
        <h2>게시판 수정</h2>
        <BoardForm @form-submit="formSubmit" submit-button-text="수정" :board="board"/>
    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import BoardForm from '@/components/board/BoardForm.vue';
    import { onMounted, reactive } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const router = useRouter();
    const board = reactive({});
    const currentRoute = useRoute();
    const fetchBoard = async(id)=>{
        try {
            const response = await apiClient.get(`/board/${id}`);
            Object.assign(board, response.data);
            console.log(response.data);
            console.log("board???"+board);
            
        } catch (error) {
            
        }
    };
    const formSubmit = async(formData)=>{
        if(!confirm(`수정할겨?`)){
            return;
        }
        try {
            const response = await apiClient.put(`/board/${formData.id}`, formData)

            console.log(response.status);
            
            if(response.status === 200){
               alert(`정상적으로 수정함`);
               router.push({name:'board'});
            }
        } catch (error) {
            if (error.response.status === 400) {
                alert(`500자이내 작성`);
            }else if(error.response.status === 404){
                alert(error.response.status);
                router.push({name:'board'});
            }else{
                alert('에러가 발생');
            }
        }
    };

    onMounted(()=>{
        fetchBoard(currentRoute.params.id);
    });

</script>