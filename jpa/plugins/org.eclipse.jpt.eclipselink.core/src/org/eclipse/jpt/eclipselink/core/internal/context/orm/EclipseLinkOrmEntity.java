package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;

public class EclipseLinkOrmEntity extends GenericOrmEntity
	implements EclipseLinkEntity
{
	protected final EclipseLinkOrmReadOnly readOnly;
	
	
	public EclipseLinkOrmEntity(OrmPersistentType parent) {
		super(parent);
		this.readOnly = getJpaFactory().buildOrmReadOnly(this);
	}
	
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	public Caching getCaching() {
		// TODO Auto-generated method stub
		return null;
	}

	public ChangeTracking getChangeTracking() {
		// TODO Auto-generated method stub
		return null;
	}

	public Customizer getCustomizer() {
		// TODO Auto-generated method stub
		return null;
	}

	public EclipseLinkOrmReadOnly getReadOnly() {
		return readOnly;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlEntity addToResourceModel(XmlEntityMappings entityMappings) {
		XmlEntity entity = EclipseLinkOrmFactory.eINSTANCE.createXmlEntity();
		getPersistentType().initialize(entity);
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	@Override
	public void initialize(XmlEntity entity) {
		super.initialize(entity);
		getReadOnly().initialize((XmlReadOnly) entity, getJavaReadOnly());
	}
	
	@Override
	public void update(XmlEntity entity) {
		super.update(entity);
		getReadOnly().update((XmlReadOnly) entity, getJavaReadOnly());
	}
	
	@Override
	public EclipseLinkJavaEntity getJavaEntity() {
		return (EclipseLinkJavaEntity) super.getJavaEntity();
	}
	
	protected JavaReadOnly getJavaReadOnly() {
		EclipseLinkJavaEntity javaEntity = getJavaEntity();
		return (javaEntity == null) ? null : javaEntity.getReadOnly();
	}
}
