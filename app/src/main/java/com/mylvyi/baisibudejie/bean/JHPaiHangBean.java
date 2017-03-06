package com.mylvyi.baisibudejie.bean;

import java.util.List;

/**
 * Created by qf on 2016/11/2.
 */
public class JHPaiHangBean {

    /**
     * count : 2074
     * np : 1.477882082E9
     */

    private InfoBean info;
    /**
     * status : 4
     * comment : 189
     * top_comments : [{"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"草，屏幕外的我也被吓到了","like_count":355,"u":{"header":["http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50","http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50"],"sex":"m","uid":"18292529","is_vip":false,"name":"菲戈SIR"},"preuid":0,"passtime":"2016-10-30 10:36:14","voiceuri":"","id":67487341},{"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"幸亏我扔手机扔的快","like_count":246,"u":{"header":["http://qzapp.qlogo.cn/qzapp/100336987/A0490B4AB2217A81459260A2E2754A8E/100","http://qzapp.qlogo.cn/qzapp/100336987/A0490B4AB2217A81459260A2E2754A8E/100"],"sex":"m","uid":"19281791","is_vip":false,"name":"几多心事讲与你"},"preuid":0,"passtime":"2016-10-30 14:36:36","voiceuri":"","id":67499260},{"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"\u200d头闪了一下的点 我看看有多少","like_count":115,"u":{"header":["http://wimg.spriteapp.cn/profile/large/2015/08/16/55cfe5555a9bd_mini.jpg","http://dimg.spriteapp.cn/profile/large/2015/08/16/55cfe5555a9bd_mini.jpg"],"sex":"m","uid":"7127497","is_vip":false,"name":"I'm 2B文艺骚年"},"preuid":0,"passtime":"2016-11-01 23:39:24","voiceuri":"","id":67684527}]
     * tags : [{"id":1,"name":"搞笑"},{"id":55,"name":"微视频"},{"id":61,"name":"恶搞"}]
     * bookmark : 798
     * text : 艾玛吓死我了，这一下真准！还好我手机拿得稳
     * up : 3531
     * share_url : http://a.f.budejie.com/share/21737298.html?wx.qq.com
     * down : 100
     * forward : 1126
     * top_comment : {"voicetime":0,"status":0,"cmt_type":"text","precid":0,"content":"草，屏幕外的我也被吓到了","like_count":355,"u":{"header":["http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50","http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50"],"sex":"m","uid":"18292529","is_vip":false,"name":"菲戈SIR"},"preuid":0,"passtime":"2016-10-30 10:36:14","voiceuri":"","id":67487341}
     * u : {"header":["http://qzapp.qlogo.cn/qzapp/100336987/1A63BA6E98F83BEFF62359A239D19EA4/100","http://qzapp.qlogo.cn/qzapp/100336987/1A63BA6E98F83BEFF62359A239D19EA4/100"],"is_v":false,"uid":"10986367","is_vip":false,"name":"老衲法号乱来MZU"}
     * passtime : 2016-11-01 19:12:01
     * video : {"playfcount":79254,"height":640,"width":352,"video":["http://wvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4"],"download":["http://wvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4"],"duration":15,"playcount":197991,"thumbnail":["http://wimg.spriteapp.cn/picture/2016/1030/58154770e0eed__b.jpg","http://dimg.spriteapp.cn/picture/2016/1030/58154770e0eed__b.jpg"],"thumbnail_small":["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1030/58154770e0eed__b.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1030/58154770e0eed__b.jpg"]}
     * type : video
     * id : 21737298
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
         * voicetime : 0
         * status : 0
         * cmt_type : text
         * precid : 0
         * content : 草，屏幕外的我也被吓到了
         * like_count : 355
         * u : {"header":["http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50","http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50"],"sex":"m","uid":"18292529","is_vip":false,"name":"菲戈SIR"}
         * preuid : 0
         * passtime : 2016-10-30 10:36:14
         * voiceuri :
         * id : 67487341
         */

        private TopCommentBean top_comment;
        /**
         * header : ["http://qzapp.qlogo.cn/qzapp/100336987/1A63BA6E98F83BEFF62359A239D19EA4/100","http://qzapp.qlogo.cn/qzapp/100336987/1A63BA6E98F83BEFF62359A239D19EA4/100"]
         * is_v : false
         * uid : 10986367
         * is_vip : false
         * name : 老衲法号乱来MZU
         */

        private UBean u;
        private String passtime;
        /**
         * playfcount : 79254
         * height : 640
         * width : 352
         * video : ["http://wvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4"]
         * download : ["http://wvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4","http://dvideo.spriteapp.cn/video/2016/1030/58154770f3514_wpd.mp4"]
         * duration : 15
         * playcount : 197991
         * thumbnail : ["http://wimg.spriteapp.cn/picture/2016/1030/58154770e0eed__b.jpg","http://dimg.spriteapp.cn/picture/2016/1030/58154770e0eed__b.jpg"]
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/picture/2016/1030/58154770e0eed__b.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/1030/58154770e0eed__b.jpg"]
         */

        private VideoBean video;
        private String type;
        private String id;
        /**
         * voicetime : 0
         * status : 0
         * cmt_type : text
         * precid : 0
         * content : 草，屏幕外的我也被吓到了
         * like_count : 355
         * u : {"header":["http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50","http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50"],"sex":"m","uid":"18292529","is_vip":false,"name":"菲戈SIR"}
         * preuid : 0
         * passtime : 2016-10-30 10:36:14
         * voiceuri :
         * id : 67487341
         */

        private List<TopCommentsBean> top_comments;
        /**
         * id : 1
         * name : 搞笑
         */

        private List<TagsBean> tags;
        /**
         * images : ["http://wimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46.gif","http://dimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46.gif"]
         * width : 360
         * gif_thumbnail : ["http://wimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46_a_1.jpg","http://dimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46_a_1.jpg"]
         * download_url : ["http://wimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46_d.jpg","http://dimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46_d.jpg","http://wimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46_a_1.jpg","http://dimg.spriteapp.cn/ugc/2016/10/30/5815c5e3e5e46_a_1.jpg"]
         * height : 360
         */

        private GifBean gif;

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

        public TopCommentBean getTop_comment() {
            return top_comment;
        }

        public void setTop_comment(TopCommentBean top_comment) {
            this.top_comment = top_comment;
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

        public GifBean getGif() {
            return gif;
        }

        public void setGif(GifBean gif) {
            this.gif = gif;
        }

        public static class TopCommentBean {
            private int voicetime;
            private int status;
            private String cmt_type;
            private int precid;
            private String content;
            private int like_count;
            /**
             * header : ["http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50","http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50"]
             * sex : m
             * uid : 18292529
             * is_vip : false
             * name : 菲戈SIR
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
             * header : ["http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50","http://app.qlogo.cn/mbloghead/4ec1cabd73d8ef828220/50"]
             * sex : m
             * uid : 18292529
             * is_vip : false
             * name : 菲戈SIR
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

        public static class GifBean {
            private int width;
            private int height;
            private List<String> images;
            private List<String> gif_thumbnail;
            private List<String> download_url;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public List<String> getGif_thumbnail() {
                return gif_thumbnail;
            }

            public void setGif_thumbnail(List<String> gif_thumbnail) {
                this.gif_thumbnail = gif_thumbnail;
            }

            public List<String> getDownload_url() {
                return download_url;
            }

            public void setDownload_url(List<String> download_url) {
                this.download_url = download_url;
            }
        }
    }
}
