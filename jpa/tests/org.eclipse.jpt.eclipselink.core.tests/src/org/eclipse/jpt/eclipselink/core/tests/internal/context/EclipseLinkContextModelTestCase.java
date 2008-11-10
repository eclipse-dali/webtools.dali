/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatform;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLinkContextModelTestCase extends ContextModelTestCase
{
	protected EclipseLinkContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetDataModelProvider());		
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, EclipseLinkJpaPlatform.ID);
		dataModel.setProperty(JpaFacetDataModelProperties.CREATE_ORM_XML, Boolean.FALSE);
		return dataModel;
	}
	
	
	@Override
	protected EclipseLinkPersistenceUnit persistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.persistenceUnit();
	}
	
	@Override
	protected EclipseLinkJavaEntity javaEntity() {
		return (EclipseLinkJavaEntity) javaPersistentType().getMapping();
	}
	
	protected ICompilationUnit createAnnotationAndMembers(String packageName, String annotationName, String annotationBody) throws Exception {
		return this.javaProject.createCompilationUnit(packageName, annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	protected ICompilationUnit createEnumAndMembers(String packageName, String enumName, String enumBody) throws Exception {
		return this.javaProject.createCompilationUnit(packageName, enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}
}
