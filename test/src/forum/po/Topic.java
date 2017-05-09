package forum.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * 帖子表
 * @author JiaShubing
 *
 */
@Entity
@Table(name="t_topic")
public class Topic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6734394733551567366L;
	/**帖子表的id*/
	private int id;						//
	private Section section;			//
	private User user;					//
	private String title;				//标题
	private String remark;				//摘要
	private Date publishTime;			//发表时间
	private Date modifyTime;			//最新时间
	private String firstimg;			//摘要图片
	private int good=0;					//是否精华
	private int top=0;					//是否置顶
	private long replySum=0;					//回复总数
	private List<Reply> replyList=new ArrayList<Reply>();
	
	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="sectionId")
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	
	@ManyToOne
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(length=50)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@OneToMany(mappedBy="topic")
	@Cascade(value={CascadeType.DELETE})
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	public long getReplySum() {
		return replySum;
	}
	public void setReplySum(long replySum) {
		this.replySum = replySum;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	
	@Column(length=40)
	public String getFirstimg() {
		return firstimg;
	}
	public void setFirstimg(String firstimg) {
		this.firstimg = firstimg;
	}
	
	
	
}
