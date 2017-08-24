package forum.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_user")
public class User implements Serializable{

	private static final long serialVersionUID = -5669788973183715241L;
	private long id;        		//
	private String nickName;	//
	private String trueName;	//
	private String password;	//
	private String sex;			//
	private String face;		//
	private Date regTime;		//
	private String email;		//
	private String mobile;		//
	private int type;			//
	
	private DbUser dbUser;
	
	private List<Section> sectionList=new ArrayList<Section>();
	private List<Topic> topicList=new ArrayList<Topic>();
	private List<Reply> replyList=new ArrayList<Reply>();
	
	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(length=20)
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Column(length=20)
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	@Column(length=50)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(length=20)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length=10)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="masterId", updatable=false)
	public List<Section> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}
	
	@OneToMany(mappedBy="user")
	@Cascade(value={CascadeType.DELETE})
	public List<Topic> getTopicList() {
		return topicList;
	}
	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}
	
	@OneToMany(mappedBy="user")
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	
	// OneToOne指定了一对一的关联关系，一对一中随便指定一方来维护映射关系，这里选择PersonInform来进行维护 
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dbUserId",unique=true,nullable=false)		//指定外键的名字dbUserId
	public DbUser getDbUser() {
		return dbUser;
	}
	public void setDbUser(DbUser dbUser) {
		this.dbUser = dbUser;
	}
	
}
