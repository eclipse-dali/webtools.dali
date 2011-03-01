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
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAbstractType;
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
 * @version 3.0
 * @since 3.0
 */
public interface JaxbType
		extends JavaContextNode {
	
	/**
	 * Return the kind of JaxbType this represents
	 */
	Kind getKind();
	
	/**
	 * Return the associated java resource type
	 */
	JavaResourceAbstractType getJavaResourceType();
	
	/**
	 * Returns the fully qualified name of this type, including qualification for any 
	 * enclosing types and packages.
	 */
	String getFullyQualifiedName();
	
	/**
	 * Returns the type-qualified name of this type, including qualification for any 
	 * enclosing types, but not including package qualification.
	 */
	String getTypeQualifiedName();
	
	/**
	 * Return the name of the type without any package or type qualifiers
	 */
	String getSimpleName();
	
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
	Iterable<String> getDirectlyReferencedTypeNames();
	
	
	// **************** validation ********************************************
	
	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter);
	
	
	/**
	 * The kind of metadata specified on the java type.
	 */
	public static enum Kind {
		
		/**
		 * A JaxbType of {@link Kind} PERSISTENT_CLASS may safely be cast as a {@link JaxbPersistentClass}
		 */
		PERSISTENT_CLASS,
		
		/**
		 * A JaxbType of {@link Kind} PERSISTENT_ENUM may safely be cast as a {@link JaxbPersistentEnum}
		 */
		PERSISTENT_ENUM,
		
		/**
		 * A JaxbType of {@link Kind} REGISTRY may safely be cast as a {@link JaxbRegistry}
		 */
		REGISTRY,
		
		/**
		 * A JaxbType of {@link Kind} TRANSIENT may safely be cast as a {@link JaxbTransientClass}
		 */
		TRANSIENT
	}
}
