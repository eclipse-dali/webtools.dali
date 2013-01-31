/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Represents a java class (or enum or interface) with JAXB metadata (specified or implied).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.1
 */
public interface JavaType
		extends XmlAdaptable {
	
	// ***** kind *****
	
	/**
	 * Return the kind of type represented.
	 * {@link JavaType}s of {@link TypeKind} CLASS may safely be cast to {@link JavaClass}
	 * {@link JavaType}s of {@link TypeKind} ENUM may safely be cast to {@link JavaEnum}
	 */
	TypeKind getKind();
	class IsKind
		extends FilterAdapter<JavaType>
	{
		private final TypeKind typeKind;
		public IsKind(TypeKind typeKind) {
			super();
			this.typeKind = typeKind;
		}
		@Override
		public boolean accept(JavaType type) {
			return type.getKind() == this.typeKind;
		}
	}
	
	
	// ***** type mapping *****
	
	final String MAPPING_PROPERTY = "mapping";  //$NON-NLS-1$
	
	/**
	 * Return the mapping of this type.  
	 * May be null.
	 * Will <b>not</b> be null if this type has an XmlType annotation (or other mapping annotation)
	 * or if this type is default mapped.
	 */
	JavaTypeMapping getMapping();
	
	
	// ***** default mapped *****
	
	final String DEFAULT_MAPPED_PROPERTY = "defaultMapped";  //$NON-NLS-1$
	
	/**
	 * Return true if this type is mapped by reference.
	 * (If this type has a default mapping by virtue of it being referenced by another mapped type.)
	 */
	boolean isDefaultMapped();
	
	/**
	 * Set this to <code>true</code> if this type is referenced by another mapped type.
	 * (NB:  should only be called by {@link JaxbContextRoot} object.)
	 */
	void setDefaultMapped(boolean newValue);
	
	
	/**
	 * Return the associated java resource type
	 */
	JavaResourceAbstractType getJavaResourceType();
	
	/**
	 * Return the type's name object
	 */
	TypeName getTypeName();
	
	/**
	 * Return the {@link JaxbPackage} associated with this type
	 * (NB:  may be null in some partially built cases)
	 */
	JaxbPackage getJaxbPackage();
	
	/**
	 * Return the {@link JaxbPackageInfo} associated with this type, if it exists
	 */
	JaxbPackageInfo getJaxbPackageInfo();
	
	
	// ****** validation *****
	
	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter);
}
