/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
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
 * @version 3.1
 * @since 3.1
 */
public interface JaxbType
		extends JavaContextNode, XmlAdaptable {
	
	// ***** kind *****
	
	Kind getKind();
	
	
	// ***** type mapping *****
	
	final String MAPPING_PROPERTY = "mapping";  //$NON-NLS-1$
	
	/**
	 * Return the mapping of this type.  
	 * May be null.
	 * Will <b>not</b> be null if this type has an XmlType annotation (or other mapping annotation)
	 * or if this type is default mapped.
	 */
	JaxbTypeMapping getMapping();
	
	
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
	 * Return the name of the type without any package or type qualifiers
	 */
	String getSimpleName();
	
	/**
	 * Returns the type-qualified name of this type, including qualification for any 
	 * enclosing types, but not including package qualification.
	 */
	String getTypeQualifiedName();
	
	/**
	 * Returns the fully qualified name of this type, including qualification for any 
	 * enclosing types and packages.
	 */
	String getFullyQualifiedName();
	
	/**
	 * Return the name of the type's package.  Empty string if none.
	 */
	String getPackageName();
	
	/**
	 * Return the {@link JaxbPackage} associated with this type
	 */
	JaxbPackage getJaxbPackage();
	
	/**
	 * Return all directly referenced types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
	
	
	// ****** validation *****
	
	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter);
	
	
	public enum Kind {
		
		/**
		 * {@link JaxbType}s of {@link Kind} CLASS may safely be cast as {@link JaxbClass}
		 */
		CLASS,
		
		/**
		 * {@link JaxbType}s of {@link Kind} ENUM may safely be cast as {@link JaxbEnum}
		 */
		ENUM;
	}
}
