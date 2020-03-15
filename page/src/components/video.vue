<template>
  <div> {{msg}}
    <div id="dplayer"></div>
    <button @click="dplayer()">test</button>
  </div>
</template>

<script>
    import 'dplayer/dist/DPlayer.min.css';
    import DPlayer from 'dplayer';

    export default {
        data() {
            return {
                msg: 'Hello liyi'
            }
        },
        mounted: function () {
            this.dplayer()
        },
        methods: {
            dplayer() {
                const dplayer = new DPlayer({
                    container: document.getElementById('dplayer'),
                    video: {
                        url: 'https://csrc.vcloud.dogecdn.com/vcloud/17/v/20190424/1556036075_818c4125ec9c8cbc7a7a8a7cc1601512/1037/7d515b22c4958598c0fbd1e6290a5ca5.mp4',
                    },
                });
            }
        }
    }
</script>

<style>

</style>


<!--2020/2/10-->
