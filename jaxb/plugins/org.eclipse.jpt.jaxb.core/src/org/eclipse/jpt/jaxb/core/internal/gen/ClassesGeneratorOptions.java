/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.gen;


/**
 *  ClassesGeneratorOptions
 */
public class ClassesGeneratorOptions
{
	private String proxy;
	private String proxyFile;
	
	private boolean usesStrictValidation;
	private boolean makesReadOnly;
	private boolean suppressesPackageInfoGen;
	private boolean suppressesHeaderGen;
	private boolean isVerbose;
	private boolean isQuiet;

	private boolean treatsAsXmlSchema;
	private boolean treatsAsRelaxNg;
	private boolean treatsAsRelaxNgCompact;
	private boolean treatsAsDtd;
	private boolean treatsAsWsdl;
	private boolean showsVersion;
	private boolean showsHelp;

	// ********** constructor **********
	
	public ClassesGeneratorOptions() {
		super();
	}

	// ********** getters/setters *********

	public String getProxy() {
		return this.proxy;
	}
	
	public void setProxy(String proxy){
		this.proxy = proxy;
	}

	public String getProxyFile() {
		return this.proxyFile;
	}
	
	public void setProxyFile(String proxyFile){
		this.proxyFile = proxyFile;
	}

	public boolean suppressesPackageInfoGen() {
		return this.suppressesPackageInfoGen;
	}

	public void setSuppressesPackageInfoGen(boolean suppressesPackageInfoGen) {
		this.suppressesPackageInfoGen = suppressesPackageInfoGen;
	}

	public boolean usesStrictValidation() {
		return this.usesStrictValidation;
	}

	public void setUsesStrictValidation(boolean usesStrictValidation) {
		this.usesStrictValidation = usesStrictValidation;
	}

	public boolean makesReadOnly() {
		return this.makesReadOnly;
	}

	public void setMakesReadOnly(boolean makesReadOnly) {
		this.makesReadOnly = makesReadOnly;
	}
	
	public boolean suppressesHeaderGen() {
		return this.suppressesHeaderGen;
	}
	
	public void setSuppressesHeaderGen(boolean suppressesHeaderGen){
		this.suppressesHeaderGen = suppressesHeaderGen;
	}

	public boolean isVerbose() {
		return this.isVerbose;
	}
	
	public void setIsVerbose(boolean isVerbose){
		this.isVerbose = isVerbose;
	}
	
	public boolean isQuiet() {
		return this.isQuiet;
	}
	
	public void setIsQuiet(boolean isQuiet){
		this.isQuiet = isQuiet;
	}

	public boolean treatsAsXmlSchema() {
		return this.treatsAsXmlSchema;
	}
	
	public void setTreatsAsXmlSchema(boolean treatsAsXmlSchema){
		this.treatsAsXmlSchema = treatsAsXmlSchema;
	}
	
	public boolean treatsAsRelaxNg() {
		return this.treatsAsRelaxNg;
	}
	
	public void setTreatsAsRelaxNg(boolean treatsAsRelaxNg){
		this.treatsAsRelaxNg = treatsAsRelaxNg;
	}
	
	public boolean treatsAsRelaxNgCompact() {
		return this.treatsAsRelaxNgCompact;
	}
	
	public void setTreatsAsRelaxNgCompact(boolean treatsAsRelaxNgCompact){
		this.treatsAsRelaxNgCompact = treatsAsRelaxNgCompact;
	}
	
	public boolean treatsAsDtd() {
		return this.treatsAsDtd;
	}
	
	public void setTreatsAsDtd(boolean treatsAsDtd){
		this.treatsAsDtd = treatsAsDtd;
	}
	
	public boolean treatsAsWsdl() {
		return this.treatsAsWsdl;
	}
	
	public void setTreatsAsWsdl(boolean treatsAsWsdl){
		this.treatsAsWsdl = treatsAsWsdl;
	}
	
	public boolean showsVersion() {
		return this.showsVersion;
	}
	
	public void setShowsVersion(boolean showsVersion){
		this.showsVersion = showsVersion;
	}
	
	public boolean showsHelp() {
		return this.showsHelp;
	}
	
	public void setShowsHelp(boolean showsHelp){
		this.showsHelp = showsHelp;
	}

}
