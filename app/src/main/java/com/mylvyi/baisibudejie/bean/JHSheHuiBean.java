package com.mylvyi.baisibudejie.bean;

import java.util.List;

/**
 * Created by qf on 2016/11/2.
 */
public class JHSheHuiBean {

    /**
     * count : 1000
     * np : 1.477996429E9
     */

    private InfoBean info;
    /**
     * status : 2
     * comment : 52
     * top_comments : [{"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"这都是些什么朋友，是老子的话哄跑完。断交","like_count":29,"u":{"header":["http://qzapp.qlogo.cn/qzapp/100336987/6B760C31EA3B4C8E5FDD1917BBA967DE/100","http://qzapp.qlogo.cn/qzapp/100336987/6B760C31EA3B4C8E5FDD1917BBA967DE/100"],"sex":"m","uid":"18840441","is_vip":false,"name":"过期的温度999"},"preuid":0,"passtime":"2016-11-02 10:34:52","voiceuri":"","id":67707246},{"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"妈的，几个大男人，一点缚鸡之力都没有，连个内裤都扒不下来，鄙视👎","like_count":22,"u":{"header":["http://wimg.spriteapp.cn/profile/large/2016/04/23/571ae2cfaf54f_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/04/23/571ae2cfaf54f_mini.jpg"],"sex":"m","uid":"18123464","is_vip":false,"name":"你含挺喜人来你"},"preuid":0,"passtime":"2016-11-02 10:46:05","voiceuri":"","id":67707806},{"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"谁要敢这么跟我闹，别说是朋友，爹妈都不认！","like_count":21,"u":{"header":["http://wimg.spriteapp.cn/profile/large/2016/01/18/569c72df5d01f_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/01/18/569c72df5d01f_mini.jpg"],"sex":"m","uid":"9384524","is_vip":false,"name":"那⒈抹微笑、"},"preuid":0,"passtime":"2016-11-02 10:35:12","voiceuri":"","id":67707262}]
     * tags : [{"id":55,"name":"微视频"},{"id":248,"name":"囧事"},{"id":473,"name":"社会新闻"}]
     * bookmark : 18
     * text : 新郎新娘婚房内被一帮男性朋友扒光，闹洞房恶搞无下限！
     * up : 213
     * share_url : http://a.f.budejie.com/share/21800169.html?wx.qq.com
     * down : 56
     * forward : 18
     * u : {"header":["http://wimg.spriteapp.cn/profile/profile/20150804090717.jpg","http://dimg.spriteapp.cn/profile/profile/20150804090717.jpg"],"is_v":false,"uid":"15456560","is_vip":false,"name":"热点追踪"}
     * passtime : 2016-11-02 10:27:02
     * video : {"playfcount":611,"height":360,"width":640,"video":["http://wvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4"],"download":["http://wvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4"],"duration":75,"playcount":7811,"thumbnail":["http://wimg.spriteapp.cn/picture/2016/1102/58194ee8b818c__b_52.jpg","http://dimg.spriteapp.cn/picture/2016/1102/58194ee8b818c__b_52.jpg"],"thumbnail_small":["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1102/58194ee8b818c__b_52.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1102/58194ee8b818c__b_52.jpg"]}
     * type : video
     * id : 21800169
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
        private String text;
        private String up;
        private String share_url;
        private int down;
        private int forward;
        /**
         * header : ["http://wimg.spriteapp.cn/profile/profile/20150804090717.jpg","http://dimg.spriteapp.cn/profile/profile/20150804090717.jpg"]
         * is_v : false
         * uid : 15456560
         * is_vip : false
         * name : 热点追踪
         */

        private UBean u;
        private String passtime;
        /**
         * playfcount : 611
         * height : 360
         * width : 640
         * video : ["http://wvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4"]
         * download : ["http://wvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1102/58194ee8d23d0_wpd.mp4"]
         * duration : 75
         * playcount : 7811
         * thumbnail : ["http://wimg.spriteapp.cn/picture/2016/1102/58194ee8b818c__b_52.jpg","http://dimg.spriteapp.cn/picture/2016/1102/58194ee8b818c__b_52.jpg"]
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1102/58194ee8b818c__b_52.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1102/58194ee8b818c__b_52.jpg"]
         */

        private VideoBean video;
        private String type;
        private String id;
        /**
         * voicetime : 0
         * status : 0
         * cmt_type : text
         * precid : 0
         * content : 这都是些什么朋友，是老子的话哄跑完。断交
         * like_count : 29
         * u : {"header":["http://qzapp.qlogo.cn/qzapp/100336987/6B760C31EA3B4C8E5FDD1917BBA967DE/100","http://qzapp.qlogo.cn/qzapp/100336987/6B760C31EA3B4C8E5FDD1917BBA967DE/100"],"sex":"m","uid":"18840441","is_vip":false,"name":"过期的温度999"}
         * preuid : 0
         * passtime : 2016-11-02 10:34:52
         * voiceuri :
         * id : 67707246
         */

        private List<TopCommentsBean> top_comments;
        /**
         * id : 55
         * name : 微视频
         */

        private List<TagsBean> tags;
        /**
         * medium : []
         * big : ["http://wimg.spriteapp.cn/ugc/2016/11/03/581a0ee56e13d_1.jpg","http://dimg.spriteapp.cn/ugc/2016/11/03/581a0ee56e13d_1.jpg"]
         * download_url : ["http://wimg.spriteapp.cn/ugc/2016/11/03/581a0ee56e13d_d.jpg","http://dimg.spriteapp.cn/ugc/2016/11/03/581a0ee56e13d_d.jpg","http://wimg.spriteapp.cn/ugc/2016/11/03/581a0ee56e13d.jpg","http://dimg.spriteapp.cn/ugc/2016/11/03/581a0ee56e13d.jpg"]
         * height : 17012
         * width : 1080
         * small : []
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/ugc/2016/11/03/581a0ee56e13d.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2016/11/03/581a0ee56e13d.jpg"]
         */

        private ImageBean image;

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

        public List<TopCommentsBean> getTop_comments() {
            return top_comments;
        }

        public void setTop_comments(List<TopCommentsBean> top_comments) {
            this.top_comments = top_comments;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public ImageBean getImage() {
            return image;
        }

        public void setImage(ImageBean image) {
            this.image = image;
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

        public static class TopCommentsBean {
            private int voicetime;
            private int status;
            private String cmt_type;
            private int precid;
            private String content;
            private int like_count;
            /**
             * header : ["http://qzapp.qlogo.cn/qzapp/100336987/6B760C31EA3B4C8E5FDD1917BBA967DE/100","http://qzapp.qlogo.cn/qzapp/100336987/6B760C31EA3B4C8E5FDD1917BBA967DE/100"]
             * sex : m
             * uid : 18840441
             * is_vip : false
             * name : 过期的温度999
             */

            private UBean u;
            private int preuid;
            private String passtime;
            private String voiceuri;
            private int id;

            public int getVoicetime() {
                return voicetime;
            }

            public void setVoicetime(int voicetime) {
                this.voicetime = voicetime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCmt_type() {
                return cmt_type;
            }

            public void setCmt_type(String cmt_type) {
                this.cmt_type = cmt_type;
            }

            public int getPrecid() {
                return precid;
            }

            public void setPrecid(int precid) {
                this.precid = precid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public UBean getU() {
                return u;
            }

            public void setU(UBean u) {
                this.u = u;
            }

            public int getPreuid() {
                return preuid;
            }

            public void setPreuid(int preuid) {
                this.preuid = preuid;
            }

            public String getPasstime() {
                return passtime;
            }

            public void setPasstime(String passtime) {
                this.passtime = passtime;
            }

            public String getVoiceuri() {
                return voiceuri;
            }

            public void setVoiceuri(String voiceuri) {
                this.voiceuri = voiceuri;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public static class UBean {
                private String sex;
                private String uid;
                private boolean is_vip;
                private String name;
                private List<String> header;

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
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

        public static class ImageBean {
            private int height;
            private int width;
            private List<?> medium;
            private List<String> big;
            private List<String> download_url;
            private List<?> small;
            private List<String> thumbnail_small;

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

            public List<?> getMedium() {
                return medium;
            }

            public void setMedium(List<?> medium) {
                this.medium = medium;
            }

            public List<String> getBig() {
                return big;
            }

            public void setBig(List<String> big) {
                this.big = big;
            }

            public List<String> getDownload_url() {
                return download_url;
            }

            public void setDownload_url(List<String> download_url) {
                this.download_url = download_url;
            }

            public List<?> getSmall() {
                return small;
            }

            public void setSmall(List<?> small) {
                this.small = small;
            }

            public List<String> getThumbnail_small() {
                return thumbnail_small;
            }

            public void setThumbnail_small(List<String> thumbnail_small) {
                this.thumbnail_small = thumbnail_small;
            }
        }
    }
}
