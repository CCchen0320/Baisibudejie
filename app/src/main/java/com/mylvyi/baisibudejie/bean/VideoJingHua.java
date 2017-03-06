package com.mylvyi.baisibudejie.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class VideoJingHua {

    /**
     * count : 4123
     * np : 1.477919162E9
     */

    private InfoBean info;
    /**
     * status : 4
     * comment : 10
     * tags : [{"id":1,"name":"搞笑"},{"id":55,"name":"微视频"},{"id":60,"name":"吐槽"},{"id":18910,"name":"hx"}]
     * bookmark : 6
     * text : 篮球史上最烂罚球，哥们你是来打球的，不是来搞笑的！
     * up : 192
     * share_url : http://a.f.budejie.com/share/21744221.html?wx.qq.com
     * down : 24
     * forward : 63
     * u : {"header":["http://wimg.spriteapp.cn/profile/large/2016/08/30/57c5913446a87_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/08/30/57c5913446a87_mini.jpg"],"is_v":true,"uid":"17274977","is_vip":false,"name":"我是笔记本"}
     * passtime : 2016-11-01 10:23:02
     * video : {"playfcount":5110,"height":320,"width":480,"video":["http://wvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4"],"download":["http://wvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4"],"duration":17,"playcount":21194,"thumbnail":["http://wimg.spriteapp.cn/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg","http://dimg.spriteapp.cn/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg"],"thumbnail_small":["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg"]}
     * type : video
     * id : 21744221
     */

    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        private int count;
        private double np;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getNp() {
            return np;
        }

        public void setNp(double np) {
            this.np = np;
        }
    }

    public static class ListBean {
        private int status;
        private String comment;
        private String bookmark;
        private String text; //文字摘要
        private String up;  //赞的人数
        private String share_url;//分享连接
        private int down;  //不攒的人数
        private int forward; // 分享人数
        private boolean isZan;
        private boolean isCai;
        /**
         * header : ["http://wimg.spriteapp.cn/profile/large/2016/08/30/57c5913446a87_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/08/30/57c5913446a87_mini.jpg"]
         * is_v : true
         * uid : 17274977
         * is_vip : false
         * name : 我是笔记本
         */

        private UBean u; //用户 头像 id。。。
        private String passtime;//发表时间
        /**
         * playfcount : 5110
         * height : 320
         * width : 480
         * video : ["http://wvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4"]
         * download : ["http://wvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.mp4"]
         * duration : 17
         * playcount : 21194
         * thumbnail : ["http://wimg.spriteapp.cn/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg","http://dimg.spriteapp.cn/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg"]
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1030/a4367e52-9e7d-11e6-85fe-d4ae5296039d_wpd.jpg"]
         */

        private VideoBean video;//视频资料列表
        private String type;//资讯类型  （video）
        private String id;
        /**
         * id : 1
         * name : 搞笑
         */
        /*"top_comments"
        *   "voicetime":0,
           "status":0,
           "cmt_type":"text",
            "precid":0,
            "content":"我真不是来看奈斯什么的，我就是单纯的来看两美女，不知道有没有人和我一样",
            "like_count":221,
            "u":{
                  "header":[
                            "http://wimg.spriteapp.cn/profile/large/2016/06/03/575160fc3c651_mini.jpg",
                            "http://dimg.spriteapp.cn/profile/large/2016/06/03/575160fc3c651_mini.jpg"
                        ],
                  "sex":"m",
                   "uid":"16326635",
                   "is_vip":false,
                   "name":"了不起的蓋茨比"
              },
               "preuid":0,
               "passtime":"2016-11-02 16:53:03",
                "voiceuri":"",
                "id":67726888*/
        private List<TopCommentBean> top_comments;

        public List<TopCommentBean> getTop_comments() {
            return top_comments;
        }

        public void setTop_comments(List<TopCommentBean> top_comments) {
            this.top_comments = top_comments;
        }

        private List<TagsBean> tags;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {
            this.down = down;
        }

        public int getForward() {
            return forward;
        }

        public void setForward(int forward) {
            this.forward = forward;
        }

        public UBean getU() {
            return u;
        }

        public void setU(UBean u) {
            this.u = u;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public boolean isZan() {
            return isZan;
        }

        public void setZan(boolean zan) {
            isZan = zan;
        }

        public boolean isCai() {
            return isCai;
        }

        public void setCai(boolean cai) {
            isCai = cai;
        }

        public static class TopCommentBean{
            private String voicetime;
            private String cmt_type;
            private String content;
            private String voiceuri;
            private UCBean u;
            private AudioBean audio;

            public String getVoicetime() {
                return voicetime;
            }

            public void setVoicetime(String voicetime) {
                this.voicetime = voicetime;
            }

            public String getCmt_type() {
                return cmt_type;
            }

            public void setCmt_type(String cmt_type) {
                this.cmt_type = cmt_type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getVoiceuri() {
                return voiceuri;
            }

            public void setVoiceuri(String voiceuri) {
                this.voiceuri = voiceuri;
            }

            public UCBean getU() {
                return u;
            }

            public void setU(UCBean u) {
                this.u = u;
            }

            public AudioBean getAudio() {
                return audio;
            }

            public void setAudio(AudioBean audio) {
                this.audio = audio;
            }

            public static class UCBean{
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
            public static class AudioBean{
                private int duration;

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }
            }


        }

        public static class UBean {
            private boolean is_v;
            private String uid;
            private boolean is_vip;
            private String name;
            private List<String> header;

            public boolean isIs_v() {
                return is_v;
            }

            public void setIs_v(boolean is_v) {
                this.is_v = is_v;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public boolean isIs_vip() {
                return is_vip;
            }

            public void setIs_vip(boolean is_vip) {
                this.is_vip = is_vip;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getHeader() {
                return header;
            }

            public void setHeader(List<String> header) {
                this.header = header;
            }
        }

        public static class VideoBean {
            private int playfcount;
            private int height;
            private int width;
            private int duration;
            private int playcount;
            private List<String> video;
            private List<String> download;
            private List<String> thumbnail;
            private List<String> thumbnail_small;

            public int getPlayfcount() {
                return playfcount;
            }

            public void setPlayfcount(int playfcount) {
                this.playfcount = playfcount;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getPlaycount() {
                return playcount;
            }

            public void setPlaycount(int playcount) {
                this.playcount = playcount;
            }

            public List<String> getVideo() {
                return video;
            }

            public void setVideo(List<String> video) {
                this.video = video;
            }

            public List<String> getDownload() {
                return download;
            }

            public void setDownload(List<String> download) {
                this.download = download;
            }

            public List<String> getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(List<String> thumbnail) {
                this.thumbnail = thumbnail;
            }

            public List<String> getThumbnail_small() {
                return thumbnail_small;
            }

            public void setThumbnail_small(List<String> thumbnail_small) {
                this.thumbnail_small = thumbnail_small;
            }
        }

        public static class TagsBean {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
