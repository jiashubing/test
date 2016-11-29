package forum.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 论坛小版块表
 * @author JiaShubing
 *
 */
@Entity
@Table(name="t_section")
public class Section {

	private int id;				//
	private String name;		//
	private User master;		//
	private String logo;		//
	private List<Topic> topicList=new ArrayList<Topic>();
	private Zone zone;			//
	private Long totalCount;
	private Long goodCount;
	private Long noReplyCount;
	/*private Section parent;*/		//
	/*private List<Section> sectionList=new ArrayList<Section>();*/
	
	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/*@ManyToOne
	@JoinColumn(name="masterId")*/
	@ManyToOne
	@JoinColumn(name="masterId",nullable=true)
	public User getMaster() {
		return master;
	}
	public void setMaster(User master) {
		this.master = master;
	}
	@OneToMany(mappedBy="section")
	public List<Topic> getTopicList() {
		return topicList;
	}
	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="zoneId")
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(Long goodCount) {
		this.goodCount = goodCount;
	}
	public Long getNoReplyCount() {
		return noReplyCount;
	}
	public void setNoReplyCount(Long noReplyCount) {
		this.noReplyCount = noReplyCount;
	}
	
	/*@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="parentId")
	public Section getParent() {
		return parent;
	}
	public void setParent(Section parent) {
		this.parent = parent;
	}*/
	
	/*@OneToMany(cascade=CascadeType.ALL,mappedBy="parent",fetch=FetchType.EAGER)
	public List<Section> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}*/
	
	
}
