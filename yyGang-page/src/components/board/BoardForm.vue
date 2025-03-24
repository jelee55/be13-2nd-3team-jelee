<template>
    <form @submit.prevent="submitClick">
        <div class="mb-3 row">
            <label for="title" class="col-sm-2 col-form-label">제목</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="title" v-model.trim="formData.title">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="content" class="col-sm-2 col-form-label">내용</label>
            <div class="col-sm-10">
                <textarea type="text" class="form-control" id="content" v-model.trim="formData.content"></textarea>
            </div>
        </div>

            <button  type="submit" class="btn btn-success" v-text="submitButtonText"></button>
            <button class="btn btn-light" type="button" @click="buttonClick">뒤로가기</button>
    </form>
</template>

<script setup>

    import { reactive, watch} from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();
    const formData = reactive({});
    const emit = defineEmits(['form-submit']);

    const props = defineProps({
        submitButtonText: String,
        board: Object
    });

    const submitClick = ()=>{
        emit('form-submit', formData)
    };

    const buttonClick = ()=>{
        router.back();    
    };


    watch(
        props.board,
        (newFormData)=>{
            // formData의 속성만 newFormData의 속성의 값으로 변경
            Object.assign(formData, newFormData);
        },
        {immediate: true} // watch가 등록될 때 즉시 한 번 실행
    );


</script>