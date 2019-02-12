/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This {@link IHyperlink} is responsible to open either the Java file or the Mappings file for a
 * certain {@link Entity}.
 *
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
public final class OpenDeclarationHyperlink implements IHyperlink {

	private String typeName;
	private IJavaProject javaProject;
	private IRegion region;
	private String hyperlinkText;

	/**
	 * Creates a new <code>OpenDeclarationHyperlink</code>.
	 *
	 * @param javaProject The {@link IJavaProject} will be used to retrieve the {@link IType}
	 * @param typeName The fully qualified type name to open in the editor
	 * @param region The {@link IRegion} represents the region to display the hyperlink within the JPQL query
	 * @param hyperlinkText The text of this {@link IHyperlink}
	 */
	public OpenDeclarationHyperlink(IJavaProject javaProject,
	                                String typeName,
	                                IRegion region,
	                                String hyperlinkText) {

		super();
		this.region = region;
		this.typeName = typeName;
		this.javaProject = javaProject;
		this.hyperlinkText = hyperlinkText;
	}

	/**
	 * {@inheritDoc}
	 */
	public IRegion getHyperlinkRegion() {
		return region;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getHyperlinkText() {
		return hyperlinkText;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTypeLabel() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void open() {

		try {

			// Retrieve the Java element
			IType type = javaProject.findType(typeName);
			IJavaElement javaElement = type.getParent();

			// Open the editor
			IEditorPart editorPart = JavaUI.openInEditor(javaElement, true, false);

			// Now select the class name
			if (editorPart instanceof ITextEditor) {
				ISourceRange range = type.getNameRange();
				((ITextEditor) editorPart).selectAndReveal(range.getOffset(), range.getLength());
			}
		}
		catch (Exception e) {
			JptJpaUiPlugin.instance().logError(e);
		}
	}
}