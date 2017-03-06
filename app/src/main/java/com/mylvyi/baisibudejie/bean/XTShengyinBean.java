package com.mylvyi.baisibudejie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qf on 2016/11/2.
 */
public class XTShengyinBean {

    /**
     * count : 4000
     * np : 1.476573184E9
     */

    private InfoBean info;
    /**
     * status : 2
     * comment : 5
     * tags : [{"id":65,"name":"音乐"},{"id":70,"name":"情感"},{"id":115,"name":"原创"},{"id":119,"name":"电台"},{"id":21629,"name":"夜曲"}]
     * bookmark : 18
     * text : 很多人不需要再见，因为只是路过而已;
     -遗忘就是我们给彼此最好的纪念。
     * up : 149
     * share_url : http://a.f.budejie.com/share/21775180.html?wx.qq.com
     * down : 37
     * forward : 24
     * u : {"header":["http://wimg.spriteapp.cn/profile/large/2016/10/25/580ef0038527c_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/10/25/580ef0038527c_mini.jpg"],"is_v":false,"uid":"2018464","is_vip":false,"name":"LinSound-哼星人"}
     * passtime : 2016-11-01 02:32:02
     * audio : {"playfcount":125,"download_url":["http://wimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://wimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG"],"height":1080,"width":607,"duration":270,"playcount":2294,"audio":["http://wvoice.spriteapp.cn/voice/2016/1101/581770469ca91.mp3"],"thumbnail":["http://wimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG"],"thumbnail_small":["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1101/581770467eca9.JPG"]}
     * type : audio
     * id : 21775180
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

    public static class ListBean implements Serializable {
        private int status;
        private String comment;
        private String bookmark;
        private String text;
        private String up;
        private String share_url;
        private int down;
        private int forward;
        private boolean isZan;
        private  boolean isCai;

        public boolean getIsZan() {
            return isZan;
        }

        public void setIsZan(boolean zan) {
            isZan = zan;
        }

        public boolean getIsCai() {
            return isCai;
        }

        public void setIsCai(boolean cai) {
            isCai = cai;
        }

        /**
         * header : ["http://wimg.spriteapp.cn/profile/large/2016/10/25/580ef0038527c_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/10/25/580ef0038527c_mini.jpg"]
         * is_v : false
         * uid : 2018464
         * is_vip : false
         * name : LinSound-哼星人
         */




        private UBean u;
        private String passtime;
        /**
         * playfcount : 125
         * download_url : ["http://wimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://wimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG"]
         * height : 1080
         * width : 607
         * duration : 270
         * playcount : 2294
         * audio : ["http://wvoice.spriteapp.cn/voice/2016/1101/581770469ca91.mp3"]
         * thumbnail : ["http://wimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/picture/2016/1101/581770467eca9.JPG"]
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1101/581770467eca9.JPG","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1101/581770467eca9.JPG"]
         */

        private AudioBean audio;
        private String type;
        private String id;
        /**
         * id : 65
         * name : 音乐
         */

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

        public AudioBean getAudio() {
            return audio;
        }

        public void setAudio(AudioBean audio) {
            this.audio = audio;
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

        public static class UBean implements Serializable{
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

        public static class AudioBean implements Serializable{
            private int playfcount;
            private int height;
            private int width;
            private int duration;
            private int playcount;
            private List<String> download_url;
            private List<String> audio;
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

            public List<String> getDownload_url() {
                return download_url;
            }

            public void setDownload_url(List<String> download_url) {
                this.download_url = download_url;
            }

            public List<String> getAudio() {
                return audio;
            }

            public void setAudio(List<String> audio) {
                this.audio = audio;
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

        public static class TagsBean implements Serializable{
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
