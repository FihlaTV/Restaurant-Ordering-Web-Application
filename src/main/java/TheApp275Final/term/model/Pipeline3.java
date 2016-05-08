package TheApp275Final.term.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PIPELINE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("3")
public class Pipeline3 extends Pipeline{
  
	@OneToMany(mappedBy = "pipeline")
	private List<Order> orders;
	
}
