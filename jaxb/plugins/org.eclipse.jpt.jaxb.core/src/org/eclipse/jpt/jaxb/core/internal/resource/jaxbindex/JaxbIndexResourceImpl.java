/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.jaxbindex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jaxb.core.internal.resource.AbstractJaxbFileResourceModel;
import org.eclipse.jpt.jaxb.core.resource.jaxbindex.JaxbIndexResource;

/**
 * JAXB index
 */
public class JaxbIndexResourceImpl
	extends AbstractJaxbFileResourceModel<Vector<String>>
	implements JaxbIndexResource
{
	public JaxbIndexResourceImpl(IFile file) {
		super(file);
	}

	@Override
	protected Vector<String> buildState() {
		return new Vector<String>();
	}

	@Override
	protected void reload() {
		this.state.clear();
		super.reload();
	}

	@Override
	protected void load(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line = reader.readLine();
		while (line != null) {
			this.state.add(line.trim());
			line = reader.readLine();
		}
	}

	public Iterable<String> getFullyQualifiedClassNames() {
		return (this.packageName == null) ?
				this.getSimpleClassNames() :
				this.getPrefixedClassNames();
	}

	protected Iterable<String> getSimpleClassNames() {
		return IterableTools.cloneSnapshot(this.state);
	}

	protected Iterable<String> getPrefixedClassNames() {
		return new TransformationIterable<String, String>(
					this.getSimpleClassNames(),
					new PrefixTransformer(this.packageName + '.')
				);
	}

	protected class PrefixTransformer
		extends TransformerAdapter<String, String>
	{
		protected final String prefix;
		protected PrefixTransformer(String prefix) {
			super();
			this.prefix = prefix;
		}
		@Override
		public String transform(String string) {
			return this.prefix + string;
		}
	}

	public JptResourceType getResourceType() {
		return RESOURCE_TYPE;
	}
}
