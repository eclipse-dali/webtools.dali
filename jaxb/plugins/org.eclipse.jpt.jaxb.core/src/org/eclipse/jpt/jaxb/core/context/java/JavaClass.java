/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;

/**
 * Represents a java class with JAXB metadata (specified or implied).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface JavaClass
		extends JavaType {
	
	// ***** overrides *****
	
	JavaResourceType getJavaResourceType();
	
	public JavaClassMapping getMapping();
	Transformer<JavaClass, JavaClassMapping> MAPPING_TRANSFORMER = new MappingTransformer();
	class MappingTransformer
		extends TransformerAdapter<JavaClass, JavaClassMapping>
	{
		@Override
		public JavaClassMapping transform(JavaClass javaClass) {
			return javaClass.getMapping();
		}
	}
	
	
	// ***** XmlRegistry *****
	
	String XML_REGISTRY_PROPERTY = "xmlRegistry";  //$NON-NLS-1$
	
	XmlRegistry getXmlRegistry();
	
	Transformer<JavaClass, XmlRegistry> XML_REGISTRY_TRANSFORMER = new XmlRegistryTransformer();
	class XmlRegistryTransformer
			extends TransformerAdapter<JavaClass, XmlRegistry> {
		@Override
		public XmlRegistry transform(JavaClass javaClass) {
			return javaClass.getXmlRegistry();
		}
	}
}
