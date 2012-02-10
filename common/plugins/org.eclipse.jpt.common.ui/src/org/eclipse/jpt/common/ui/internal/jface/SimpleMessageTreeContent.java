/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Tree content with a root with a single child, the message that will be
 * displayed in the tree widget.
 * <p>
 * To display a message in a {@link org.eclipse.jface.viewers.TreeViewer}:
 * <pre>
 *     TreeViewer treeViewer = ...;
 *     treeViewer.setInput(null);
 *     treeViewer.setContentProvider(SimpleMessageTreeContent.contentProvider());
 *     treeViewer.setLabelProvider(SimpleMessageTreeContent.labelProvider());
 *     treeViewer.setInput(new SimpleMessageTreeContent("message"));
 * </pre>
 */
public class SimpleMessageTreeContent {
	private final String message;

	public SimpleMessageTreeContent(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.message);
	}

	public String getMessage() {
		return this.message;
	}


	// ********** tree content provider **********

	public static ITreeContentProvider contentProvider() {
		return CONTENT_PROVIDER;
	}

	private static final ITreeContentProvider CONTENT_PROVIDER = new ContentProvider();

	/**
	 * Content provider for message tree content.
	 */
	/* CU private */ static class ContentProvider
		extends TreeContentProvider
	{
		@Override
		public boolean hasChildren(Object element) {
			return (element instanceof SimpleMessageTreeContent);
		}

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof SimpleMessageTreeContent) {
				String msg = ((SimpleMessageTreeContent) element).getMessage();
				return new Object[] { msg };
			}
			return EMPTY_ARRAY;
		}
	}


	// ********** label provider **********

	public static ILabelProvider labelProvider() {
		return LABEL_PROVIDER;
	}

	private static final ILabelProvider LABEL_PROVIDER = new LabelProvider();

	/**
	 * Label provider for message tree content.
	 */
	/* CU private */ static class LabelProvider
		extends org.eclipse.jface.viewers.LabelProvider
	{
		@Override
		public String getText(Object element) {
			return (element instanceof String) ? (String) element : null;
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}
}
