/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;



/**
 * Java package fragment
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JavaResourcePackageFragment
	extends JavaResourceModel
{
	/**
	 * Return the package fragment's class files that contain "annotated" types.
	 * Annotated with the annotations we care about, 
	 * @see AnnotationProvider
	 */
	Iterable<JavaResourceClassFile> getClassFiles();
		String CLASS_FILES_COLLECTION = "classFiles"; //$NON-NLS-1$

	/**
	 * Return the size of the package fragment's class files.
	 */
	int getClassFilesSize();

	/**
	 * Return the package fragment's Java types.
	 * This is a convenience method that returns the JavaResourceTypes of the classFiles.
	 */
	Iterable<JavaResourceAbstractType> getTypes();
	Transformer<JavaResourcePackageFragment, Iterable<JavaResourceAbstractType>> TYPES_TRANSFORMER = new TypesTransformer();
	class TypesTransformer
		extends TransformerAdapter<JavaResourcePackageFragment, Iterable<JavaResourceAbstractType>>
	{
		@Override
		public Iterable<JavaResourceAbstractType> transform(JavaResourcePackageFragment jrpf) {
			return jrpf.getTypes();
		}
	}
}
