package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable;

public class EclipseLinkOrmMutable extends AbstractXmlContextNode 
	implements Mutable
{
	protected XmlMutable resource;
	
	protected boolean defaultMutable;
	
	protected Boolean specifiedMutable;
	
	
	public EclipseLinkOrmMutable(OrmAttributeMapping parent) {
		super(parent);
	}
	
	
	public boolean isMutable() {
		return (getSpecifiedMutable() != null) ? getSpecifiedMutable() : isDefaultMutable();
	}
	
	public boolean isDefaultMutable() {
		return this.defaultMutable;
	}
	
	protected void setDefaultMutable(boolean newDefaultMutable) {
		boolean oldDefaultMutable = this.defaultMutable;
		this.defaultMutable = newDefaultMutable;
		firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, oldDefaultMutable, newDefaultMutable);
	}
	
	public Boolean getSpecifiedMutable() {
		return this.specifiedMutable;
	}
	
	public void setSpecifiedMutable(Boolean newSpecifiedMutable) {
		Boolean oldSpecifiedMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;
		this.resource.setMutable(newSpecifiedMutable);
		firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, oldSpecifiedMutable, newSpecifiedMutable);
	}
	
	
	// **************** initialize/update **************************************
	
	protected void initialize(XmlMutable resource) {
		this.resource = resource;
		this.defaultMutable = calculateDefaultMutable();
		this.specifiedMutable = resource.getMutable();
	}
	
	protected void update(XmlMutable resource) {
		this.resource = resource;
		setDefaultMutable(calculateDefaultMutable());
		setSpecifiedMutable(resource.getMutable());
	}
	
	protected boolean calculateDefaultMutable() {
		// TODO consult persistence.xml property
		return false;
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getMutableTextRange();
	}
}
