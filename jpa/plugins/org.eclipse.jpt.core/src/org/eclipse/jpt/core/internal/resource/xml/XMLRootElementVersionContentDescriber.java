/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jesper Steen Moeller - added namespace support
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.internal.content.ContentMessages;
import org.eclipse.core.internal.runtime.RuntimeLog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.XMLContentDescriber;
import org.eclipse.osgi.util.NLS;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//TODO bug 263976 - copied from org.eclipse.core.runtime.content.XMLRootElementContentDescriber2
/**
 * A content describer for detecting the name of the top-level element, 
 * its namespace and the DTD system identifier in an XML file.
 * <p>
 * This executable extension supports "element" parameter, that 
 * can be specified more than once. If the
 * <code>":-"</code> method is used, then the value is treated as
 * "element" (always just one)
 * </p>
 * <p>
 * The value of "element" is specified using such a format
 * <code>{namespace}name/dtd:version</code>. The namespace or dtd part  
 * can be omitted and accepted are values like <code>name/dtd</code>,
 * <code>{ns}name</code> and <code>name</code>.
 * </p>
 * <p>
 * The describer will detect a document, if it matches at least one "element"
 * what means, that dtd, namespace (if specified) and name in "element"
 * match those in the document.
 * </p>
 * <p>
 * If the "element" name part is "*", e.g. <code>{namespace}*</code>, 
 * it denotes a wildcard match. If the "element" namespace part is empty, 
 * e.g. <code>{}name</code>, only these documents with the root element 
 * that belong to the unnamed namespace <code><elem xmlns=""></code> 
 * will be detected.
 * </p>
 * <p>
 * This class should be used instead of {@link XMLRootElementVersionContentDescriber}
 * which doesn't detect namespaces and doesn't allow to specify 
 * more than one set of dtds, root element names and namespaces which 
 * should be detected.
 * </p>
 * <p>
 * This class is not intended to be subclassed or instantiated by clients, 
 * only to be referenced by the "describer" configuration element in
 * extensions to the <code>org.eclipse.core.runtime.contentTypes</code>
 * extension point.
 * </p>
 * 
 * @since org.eclipse.core.contenttype 3.3
 */
public final class XMLRootElementVersionContentDescriber extends XMLContentDescriber implements IExecutableExtension {
	private static final String ELEMENT_TO_FIND = "element"; //$NON-NLS-1$
	
	/* (Intentionally not included in javadoc)
	 * The top-level elements we are looking for. This value will be initialized
	 * by the <code>setInitializationData</code> method. If no value is
	 * provided, then this means that we don't care what the top-level element
	 * will be. The list is a collection of <code>QualifiedElement</code>. 
	 */
	private QualifiedElement[] elementsToFind = null;

	/* (Intentionally not included in javadoc)
	 * Simple value holder for root element name, its namespace and dtd.
	 */
	 static class QualifiedElement {
		private String namespace;
		private String element;
		private String dtd;
		private String version;
		
		QualifiedElement(String namespace, String element, String dtd, String version) {
			this.namespace = namespace;
			this.element = element;
			this.dtd = dtd;
			this.version = version;
		}

		QualifiedElement(String qualifiedElement) {
			// Extract namespace part
			int openBrace = qualifiedElement.indexOf('{');
			int closeBrace = qualifiedElement.indexOf('}');
			if (openBrace == 0 && closeBrace >=1 ) {
				namespace = qualifiedElement.substring(1, closeBrace);
				qualifiedElement = qualifiedElement.substring(closeBrace+1);
			}
			// Extract dtd part
			int dtdSlash = qualifiedElement.indexOf('/');
			if (dtdSlash > 0) {
				dtd = qualifiedElement.substring(dtdSlash+1);
				qualifiedElement = qualifiedElement.substring(0, dtdSlash);
			}
			// Extract version part
			int versionSlash = qualifiedElement.indexOf(':');
			if (versionSlash > 0) {
				version = qualifiedElement.substring(versionSlash + 1);
				qualifiedElement = qualifiedElement.substring(0, versionSlash);
			}
			
			// Check if the name is a wildcard
			element = ("*".equals(qualifiedElement) ? null : qualifiedElement);
		}

		public String getNamespace() {
			return namespace;
		}
		
		public String getElement() {
			return element;
		}
		public String getDTD() {
			return dtd;
		}
		
		public String getVersion() {
			return version;
		}
		
		public boolean matches(String someNamespace, String someElement, String someDtd, String someVersion) {
			boolean nsMatch = this.namespace != null ? this.namespace.equals(someNamespace) : true;
			boolean elementEquals = this.element != null ? this.element.equals(someElement) : true;
			boolean dtdEquals = this.dtd != null ? this.dtd.equals(someDtd) : true;
			boolean versionEquals = this.version != null ? this.version.equals(someVersion) : true;
			return nsMatch && elementEquals && dtdEquals && versionEquals;
		}
	}
	
	/* (Intentionally not included in javadoc)
	 * Determines the validation status for the given contents.
	 * 
	 * @param contents the contents to be evaluated
	 * @return one of the following:<ul>
	 * <li><code>VALID</code></li>,
	 * <li><code>INVALID</code></li>,
	 * <li><code>INDETERMINATE</code></li>
	 * </ul>
	 * @throws IOException
	 */
	private int checkCriteria(InputSource contents) throws IOException {
		XMLRootHandler xmlHandler = new XMLRootHandler(elementsToFind != null);
		try {
			if (!xmlHandler.parseContents(contents))
				return INDETERMINATE;
		} catch (SAXException e) {
			// we may be handed any kind of contents... it is normal we fail to parse
			return INDETERMINATE;
		} catch (ParserConfigurationException e) {
			// some bad thing happened - force this describer to be disabled
			String message = ContentMessages.content_parserConfiguration;
			RuntimeLog.log(new Status(IStatus.ERROR, ContentMessages.OWNER_NAME, 0, message, e));
			throw new RuntimeException(message);
		}
		// Check to see if we matched our criteria.
		if (elementsToFind != null) {
			boolean foundOne = false;
			for (int i = 0; i < elementsToFind.length && !foundOne; ++i) {
				foundOne |= elementsToFind[i].matches(xmlHandler.getRootNamespace(), xmlHandler.getRootName(), xmlHandler.getDTD(), xmlHandler.getVersion());
			}
			if (!foundOne)
				return INDETERMINATE;
		}
		// We must be okay then.		
		return VALID;
	}

	/* (Intentionally not included in javadoc)
	 * @see IContentDescriber#describe(InputStream, IContentDescription)
	 */
	@Override
	public int describe(InputStream contents, IContentDescription description) throws IOException {
		// call the basic XML describer to do basic recognition
		if (super.describe(contents, description) == INVALID)
			return INVALID;
		// super.describe will have consumed some chars, need to rewind		
		contents.reset();
		// Check to see if we matched our criteria.		
		return checkCriteria(new InputSource(contents));
	}

	/* (Intentionally not included in javadoc)
	 * @see IContentDescriber#describe(Reader, IContentDescription)
	 */
	@Override
	public int describe(Reader contents, IContentDescription description) throws IOException {
		// call the basic XML describer to do basic recognition
		if (super.describe(contents, description) == INVALID)
			return INVALID;
		// super.describe will have consumed some chars, need to rewind
		contents.reset();
		// Check to see if we matched our criteria.
		return checkCriteria(new InputSource(contents));
	}

	/* (Intentionally not included in javadoc)
	 * @see IExecutableExtension#setInitializationData
	 */
	public void setInitializationData(final IConfigurationElement config, final String propertyName, final Object data) throws CoreException {
		if (data instanceof String)
			elementsToFind = new QualifiedElement[] {new QualifiedElement((String) data)};
		else if (data instanceof Hashtable) {
			List elements = null;

			// the describer parameters have to be read again, because "element" parameter can be specified multiple times 
			// and the given hashtable carries only one of them
			IConfigurationElement describerElement = config.getChildren("describer")[0]; //$NON-NLS-1$
			IConfigurationElement[] params = describerElement.getChildren("parameter"); //$NON-NLS-1$
			String pname = null;
			for (int i = 0; i < params.length; i++) {
				pname = params[i].getAttribute("name"); //$NON-NLS-1$
				if (ELEMENT_TO_FIND.equals(pname)) {
					if (elements == null)
						elements = new LinkedList();
					elements.add(new QualifiedElement(params[i].getAttribute("value"))); //$NON-NLS-1$
				}
			}

			List qualifiedElements = new ArrayList();

			// create list of qualified elements
			if (elements != null) {
				for (Iterator it = elements.iterator(); it.hasNext();) {
					qualifiedElements.add(it.next());
				}
			}
			elementsToFind = (QualifiedElement[]) qualifiedElements.toArray(new QualifiedElement[qualifiedElements.size()]);
		}

		if (elementsToFind.length == 0) {
			String message = NLS.bind(ContentMessages.content_badInitializationData, XMLRootElementVersionContentDescriber.class.getName());
			throw new CoreException(new Status(IStatus.ERROR, ContentMessages.OWNER_NAME, 0, message, null));
		}
	}
}
