package com.sampullara.maven;

import junit.framework.TestCase;

import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * TODO: Edit this
 * <p/>
 * User: sam
 * Date: Dec 15, 2007
 * Time: 3:39:40 PM
 */
public class MCTest extends TestCase {
    public void testHelp() throws IOException, XmlPullParserException {
        MC.main(new String[] { "help" });
    }
}
