package com.mylvyi.baisibudejie.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class DrawBean {
    /**
     * count : 4194
     * np : 1.478048041E9
     */

    private InfoBean info;
    /**
     * status : 4
     * comment : 14
     * tags : [{"id":1,"name":"搞笑"},{"id":61,"name":"恶搞"},{"id":117,"name":"美女"}]
     * bookmark : 40
     * text : 老司机，开慢点！老娘衣服快hold不住啦
     * gif : {"images":["http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23.gif","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23.gif"],"width":231,"gif_thumbnail":["http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg"],"download_url":["http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_d.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_d.jpg","http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg"],"height":307}
     * up : 123
     * share_url : http://a.f.budejie.com/share/21796047.html?wx.qq.com
     * down : 17
     * forward : 17
     * u : {"header":["http://wimg.spriteapp.cn/profile/large/2016/07/02/5777d3d140c1a_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/07/02/5777d3d140c1a_mini.jpg"],"is_v":true,"uid":"15552724","is_vip":false,"name":"曾曾"}
     * passtime : 2016-11-02 16:47:57
     * type : gif
     * id : 21796047
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
        /**
         * images : ["http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23.gif","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23.gif"]
         * width : 231
         * gif_thumbnail : ["http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg"]
         * download_url : ["http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_d.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_d.jpg","http://wimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/5818bfbc1cf23_a_1.jpg"]
         * height : 307
         */

        private GifBean gif;
        private String up;
        private String share_url;
        private int down;
        private int forward;
        /**
         * header : ["http://wimg.spriteapp.cn/profile/large/2016/07/02/5777d3d140c1a_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/07/02/5777d3d140c1a_mini.jpg"]
         * is_v : true
         * uid : 15552724
         * is_vip : false
         * name : 曾曾
         */

        private UBean u;
        private String passtime;
        private String type;
        private String id;
        /**
         * id : 1
         * name : 搞笑
         */

        private List<TagsBean> tags;
        /**
         * medium : []
         * big : ["http://wimg.spriteapp.cn/ugc/2016/11/02/58191b5621561_1.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/58191b5621561_1.jpg"]
         * download_url : ["http://wimg.spriteapp.cn/ugc/2016/11/02/58191b5621561_d.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/58191b5621561_d.jpg","http://wimg.spriteapp.cn/ugc/2016/11/02/58191b5621561.jpg","http://dimg.spriteapp.cn/ugc/2016/11/02/58191b5621561.jpg"]
         * height : 6395
         * width : 864
         * small : []
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/ugc/2016/11/02/58191b5621561.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2016/11/02/58191b5621561.jpg"]
         */

        private ImageBean image;
        /**
         * voicetime : 0
         * status : 0
         * cmt_type : text
         * precid : 0
         * content : 没有郭德纲它是个什么？
         * like_count : 14
         * u : {"header":["http://qzapp.qlogo.cn/qzapp/100336987/A61660BC85B70678A39EE64F2B67BFD0/100","http://qzapp.qlogo.cn/qzapp/100336987/A61660BC85B70678A39EE64F2B67BFD0/100"],"sex":"m","uid":"8142879","is_vip":false,"name":"大虾99999等人"}
         * preuid : 0
         * passtime : 2016-11-02 15:41:05
         * voiceuri :
         * id : 67723458
         */

        private List<TopCommentsBean> top_comments;

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

        public GifBean getGif() {
            return gif;
        }

        public void setGif(GifBean gif) {
            this.gif = gif;
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

        public ImageBean getImage() {
            return image;
        }

        public void setImage(ImageBean image) {
            this.image = image;
        }

        public List<TopCommentsBean> getTop_comments() {
            return top_comments;
        }

        public void setTop_comments(List<TopCommentsBean> top_comments) {
            this.top_comments = top_comments;
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

        public static class TopCommentsBean {
            private int voicetime;
            private int status;
            private String cmt_type;
            private int precid;
            private String content;
            private int like_count;
            /**
             * header : ["http://qzapp.qlogo.cn/qzapp/100336987/A61660BC85B70678A39EE64F2B67BFD0/100","http://qzapp.qlogo.cn/qzapp/100336987/A61660BC85B70678A39EE64F2B67BFD0/100"]
             * sex : m
             * uid : 8142879
             * is_vip : false
             * name : 大虾99999等人
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
    }
}
