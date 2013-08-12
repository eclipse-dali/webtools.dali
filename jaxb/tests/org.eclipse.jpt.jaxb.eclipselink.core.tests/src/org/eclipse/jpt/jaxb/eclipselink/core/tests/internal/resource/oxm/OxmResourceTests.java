/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.oxm;

import java.io.ByteArrayInputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.tests.internal.resource.java.JavaResourceModelTestCase;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.oxm.OxmXmlResourceProvider;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy;

@SuppressWarnings("nls")
public class OxmResourceTests
		extends JavaResourceModelTestCase {
	
	public OxmResourceTests(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return new AnnotationDefinition[0];
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return new NestableAnnotationDefinition[0];
	}
	
	public void testMultiObjectTranslators() throws Exception {
		IFile oxmFile = this.getProject().getFile("oxm.xml");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(CR);
		sb.append("<xml-bindings").append(CR);
		sb.append("    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"").append(CR);
		sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append(CR);
		sb.append("    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"").append(CR);
		sb.append("    version=\"2.4\">").append(CR);
		sb.append("    <java-types>").append(CR);
		sb.append("        <java-type>").append(CR);
		sb.append("            <java-attributes>").append(CR);
		sb.append("                <xml-attribute>").append(CR);
		sb.append("                    <xml-null-policy/>").append(CR);
		sb.append("                </xml-attribute>").append(CR);
		sb.append("            </java-attributes>").append(CR);
		sb.append("        </java-type>").append(CR);
		sb.append("    </java-types>").append(CR);
		sb.append("</xml-bindings>").append(CR);
		oxmFile.create(new ByteArrayInputStream(sb.toString().getBytes()), true, null);
		JptXmlResource resource = OxmXmlResourceProvider.getXmlResourceProvider(oxmFile).getXmlResource();
		EXmlBindings xmlBindings = (EXmlBindings) resource.getRootObject();
		
		assertNotNull(xmlBindings);
		
		EJavaType javaType = xmlBindings.getJavaTypes().get(0);
		
		assertNotNull(javaType);
		
		EXmlAttribute xmlAttribute = (EXmlAttribute) javaType.getJavaAttributes().get(0);
		
		assertNotNull(xmlAttribute);
		
		EXmlNullPolicy xmlNullPolicy = (EXmlNullPolicy) xmlAttribute.getXmlAbstractNullPolicy();
		
		assertNotNull(xmlNullPolicy);
	}
}
