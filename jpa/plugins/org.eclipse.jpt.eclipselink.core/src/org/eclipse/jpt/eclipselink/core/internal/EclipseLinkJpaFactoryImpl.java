/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaCachingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEntityImpl;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmResourceModel;

public class EclipseLinkJpaFactoryImpl extends GenericJpaFactory implements EclipseLinkJpaFactory
{
	protected EclipseLinkJpaFactoryImpl() {
		super();
	}

	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, persistenceUnit);
	}
	
	@Override
	public EclipseLinkJavaEntity buildJavaEntity(JavaPersistentType parent) {
		return new EclipseLinkJavaEntityImpl(parent);
	}
	
	public EclipseLinkJavaCaching buildEclipseLinkJavaCaching(JavaTypeMapping parent) {
		return new EclipseLinkJavaCachingImpl(parent);
	}
	
	@Override
	public ResourceModel buildResourceModel(JpaProject jpaProject, IFile file, String contentTypeId) {
		if (JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildEclipseLinkOrmResourceModel(file);
		}
		return super.buildResourceModel(jpaProject, file, contentTypeId);
	}
	
	protected ResourceModel buildEclipseLinkOrmResourceModel(IFile file) {
		return new EclipseLinkOrmResourceModel(file);
	}

}
