/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConversionValue;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConvert;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaStructConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.java.JavaJoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaMutable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaPrivateOwnable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkOrmXml;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;

/**
 * Use EclipseLinkJpaFactory to create any EclispeLink specific
 * java, orm, or persistence context model objects.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkJpaFactory extends JpaFactory
{	
	EclipseLinkOrmXml buildEclipseLinkOrmXml(MappingFileRef parent, EclipseLinkOrmResource resource);
	
	JavaCaching buildJavaCaching(JavaTypeMapping parent);
	
	JavaExpiryTimeOfDay buildJavaExpiryTimeOfDay(JavaCaching parent);
	
	EclipseLinkJavaConvert buildEclipseLinkJavaConvert(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);
	
	EclipseLinkJavaConverter buildEclipseLinkJavaConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm);
	
	EclipseLinkJavaTypeConverter buildEclipseLinkJavaTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm);
	
	EclipseLinkJavaObjectTypeConverter buildEclipseLinkJavaObjectTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm);
	
	EclipseLinkJavaStructConverter buildEclipseLinkJavaStructConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm);
	
	JavaConversionValue buildJavaConversionValue(EclipseLinkJavaObjectTypeConverter parent);
	
	JavaJoinFetchable buildJavaJoinFetchable(JavaAttributeMapping parent);
	
	JavaPrivateOwnable buildJavaPrivateOwnable(JavaAttributeMapping parent);
	
	JavaConverterHolder buildJavaConverterHolder(JavaTypeMapping parent);
	
	JavaCustomizer buildJavaCustomizer(JavaTypeMapping parent);
	
	JavaMutable buildJavaMutable(JavaAttributeMapping parent);
	
	JavaReadOnly buildJavaReadOnly(JavaTypeMapping parent);
	
	//********* covariant overrides ***********
	
	EclipseLinkJavaEntity buildJavaEntity(JavaPersistentType parent);
	
	EclipseLinkJavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent);
}
