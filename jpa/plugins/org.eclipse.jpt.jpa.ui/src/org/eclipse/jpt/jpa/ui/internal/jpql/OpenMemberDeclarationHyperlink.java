/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
public class OpenMemberDeclarationHyperlink implements IHyperlink {

	private String hyperlinkText;
	private IJavaProject javaProject;
	private ISourceReference member;
	private IRegion region;
	private String typeName;

	/**
	 * Creates a new <code>OpenMemberDeclarationHyperlink</code>.
	 */
	public OpenMemberDeclarationHyperlink(IJavaProject javaProject,
	                                      String typeName,
	                                      ISourceReference member,
	                                      IRegion region,
	                                      String hyperlinkText) {

		super();
		this.region = region;
		this.typeName = typeName;
		this.member = member;
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

			// Now select the member
			if (editorPart instanceof ITextEditor) {
				ISourceRange range = member.getNameRange();
				((ITextEditor) editorPart).selectAndReveal(range.getOffset(), range.getLength());
			}
		}
		catch (Exception e) {
			JptJpaUiPlugin.instance().logError(e);
		}
	}
}