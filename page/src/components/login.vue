<template>
  <div class="msg">{{msg}}</div>
</template>

<script>
    import layui from 'layui-src';
    export default {
        data() {
            return {
                msg: '登录'
            }
        },
        methods: {
            login(){
                layui.use('from',function () {

                })
            }
        }
    }
</script>


<style>
  .msg{
    background-color: #4815ff;
  }
</style>
