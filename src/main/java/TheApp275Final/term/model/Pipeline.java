package TheApp275Final.term.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="PIPELINE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="PIPELINE_NO",
    discriminatorType=DiscriminatorType.INTEGER
)
public class Pipeline {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PIPELINE_ID")
	private Long pipelineId ;

	 @Column(name = "PIPELINE_NO", insertable = false, updatable = false)
	 private int pipelineNo;

	public int getPipelineNo() {
		return pipelineNo;
	}

	public void setPipelineNo(int pipelineNo) {
		this.pipelineNo = pipelineNo;
	}

	@Override
	public String toString() {
		return "Pipeline [pipelineId=" + pipelineId + ", pipelineNo=" + pipelineNo + "]";
	}

	public Long getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(Long pipelineId) {
		this.pipelineId = pipelineId;
	}

}
