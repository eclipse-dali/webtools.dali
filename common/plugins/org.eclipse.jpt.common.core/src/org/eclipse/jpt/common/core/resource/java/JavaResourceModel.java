/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Common interface for Java resource nodes (source code or binary).
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
public interface JavaResourceModel
	extends Model
{
	/**
	 * Return the model's parent.
	 */
	JavaResourceModel getParent();

	/**
	 * Return the Eclipse file that contains the Java resource node
	 * (typically either a Java source code file or a JAR).
	 */
	IFile getFile();
	Transformer<JavaResourceModel, IFile> FILE_TRANSFORMER = new FileTransformer();
	class FileTransformer
		extends TransformerAdapter<JavaResourceModel, IFile>
	{
		@Override
		public IFile transform(JavaResourceModel table) {
			return table.getFile();
		}
	}

	/**
	 * Return the root of the Java resource containment hierarchy
	 * (typically either a compilation unit or a package fragment root).
	 */
	Root getRoot();

	/**
	 * Return the [source] node's root (the compilation unit).
	 */
	// TODO get rid of this method...?
	JavaResourceCompilationUnit getJavaResourceCompilationUnit();

	/**
	 * Return the [source] node's text range in the compilation unit's file.
	 */
	TextRange getTextRange();

	/**
	 * Root of Java resource model containment hierarchy.
	 */
	interface Root
		extends JavaResourceModel, JptResourceModel
	{
		/**
		 * Return the root's Java resource "abstract" types.
		 */
		Iterable<JavaResourceAbstractType> getTypes();
			String TYPES_COLLECTION = "types"; //$NON-NLS-1$
		Transformer<Root, Iterable<JavaResourceAbstractType>> TYPES_TRANSFORMER = new TypesTransformer();
		class TypesTransformer
			extends TransformerAdapter<Root, Iterable<JavaResourceAbstractType>>
		{
			@Override
			public Iterable<JavaResourceAbstractType> transform(Root root) {
				return root.getTypes();
			}
		}

		/**
		 * Called (via a hook in change notification) whenever anything in the
		 * Java resource model changes. Forwarded to listeners.
		 */
		void resourceModelChanged();
		
		/**
		 * Return the annotation provider that supplies the annotations found
		 * in the Java resource model.
		 */
		AnnotationProvider getAnnotationProvider();
	}
}
