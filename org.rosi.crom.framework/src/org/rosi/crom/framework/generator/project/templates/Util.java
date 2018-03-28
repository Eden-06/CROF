/**
 * 
 */
package org.rosi.crom.framework.generator.project.templates;


import java.io.File;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;

import crom_l1_composed.Model;
import crom_l1_composed.NamedElement;
import crom_l1_composed.RoleType;

/**
 * This is a simple utility class to store and retrieve the modelURI of the
 * currently processed Ecore model instance (xmi). Most importantly it defines a
 * getModelUri() method that emits a corresponding string representation.
 * 
 * @author eden06
 *
 */
public class Util {

	public static String bundleName = "";
	public static String bundleSymbolicName = "";

	public Util() {
	}

	/**
	 * Set the modelUri as static variable accessed via the Util objects.
	 * 
	 * @param m
	 *            the modelURI to be set
	 */
	
	public String getBundleName() {
		if (bundleName == "")
			return "bundleName";
		return bundleName.toString();
	}

	public static void setBundleName(String str) {
		bundleName = str;
	}
	
	public String getBundleSymbolicName() {
		if (bundleSymbolicName == "")
			return "bundleSymbolicName";
		return bundleSymbolicName.toString();
	}

	public static void setBundleSymbolicName(String str) {
		bundleSymbolicName = str;
	}
	
}
