package forum.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 帖子内容表
 * @author JiaShubing
 *
 */
@Entity
@Table(name="t_topic_content")
public class TopicContent implements Serializable{

	private static final long serialVersionUID = -6048877910376797346L;
	
	
	private long id;
	private String content;				//内容
	private long topicId;
	
	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Lob
	@Column(columnDefinition="TEXT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

}
