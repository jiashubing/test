//package shubing.study.po;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//@Entity
//public class Items {
//	
//	@Id 
//	@GeneratedValue
//	private Integer id;
//	
//	@Column(length=30)
//    private String name;
//
//	@Column(nullable=false)
//    private Float price=0f;
//    
//    @Column(length=50)
//    private String pic;
//    
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createtime;
//    
//    @Column(length=50)
//    private String detail;
//    
//    
//    public Items() {
//	}
//
//    public Items(String name) {
//		this.name = name;
//	}
//
//	public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name == null ? null : name.trim();
//    }
//
//    public Float getPrice() {
//        return price;
//    }
//
//    public void setPrice(Float price) {
//        this.price = price;
//    }
//
//    public String getPic() {
//        return pic;
//    }
//
//    public void setPic(String pic) {
//        this.pic = pic == null ? null : pic.trim();
//    }
//
//    public Date getCreatetime() {
//        return createtime;
//    }
//
//    public void setCreatetime(Date createtime) {
//        this.createtime = createtime;
//    }
//
//    public String getDetail() {
//        return detail;
//    }
//
//    public void setDetail(String detail) {
//        this.detail = detail == null ? null : detail.trim();
//    }
//}
