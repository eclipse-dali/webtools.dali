/*******************************************************************************
 * Copyright (c) 2010, 2013 Red Hat, Inc. and others.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * @author Dmitry Geraskov
 *
 * Java source code of package-info
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourcePackage
		extends JavaResourceAnnotatedElement {

	/**
	 * Synchronize the [source] package with the specified AST PackageDeclaration.
	 */
	void synchronizeWith(PackageDeclaration pd);

	/**
	 * The Java resource persistent package's name.
	 */
	String getName();
	String NAME_PROPERTY = "name"; //$NON-NLS-1$
	Transformer<JavaResourcePackage, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<JavaResourcePackage, String>
	{
		@Override
		public String transform(JavaResourcePackage pkg) {
			return pkg.getName();
		}
	}
}
