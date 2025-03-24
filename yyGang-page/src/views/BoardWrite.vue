<template>
    <main>
        <h2>게시판 작성</h2>
        <BoardForm @form-submit="formSubmit" submit-button-text="등록"/>
    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import BoardForm from '@/components/board/BoardForm.vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const formSubmit= async(formData)=>{
        try {
            const response = await apiClient.post(`/board`,formData);

            console.log("formData==" + formData.title);
            console.log("formData==" + formData.content);
            

            if(response.status === 201){
                alert(`정상적으로 등록`);

                router.push({name:'board'});
            }
        } catch (error) {
            
        }
    }

</script>