package com.dabo.xunuo.app.web.vo;

import com.dabo.xunuo.base.util.StringUtils;

/**
 * 版本号
 */
public class Version implements Comparable<Version>{

    private static final int VERSION_LENGTH = 3;
    private int major;//主版本号
    private int minor;//子版本号
    private int revision;//修正版本号
    private static final int HASHCODE_BASE = 31;

    public static Version getInstance(String version) {
        if (StringUtils.isEmpty(version)) {
            return null;
        }
        String[] array = version.split("\\.");
        if (array.length < VERSION_LENGTH - 1 || array.length > VERSION_LENGTH) {
            return null;
        }
        int mainVersion = Integer.parseInt(array[0]);
        int minorVersion = Integer.parseInt(array[1]);
        int revisionVersion = 0;
        if (array.length == VERSION_LENGTH) {
            revisionVersion = Integer.parseInt(array[VERSION_LENGTH - 1]);
        }
        Version instance = new Version();
        instance.setMajor(mainVersion);
        instance.setMinor(minorVersion);
        instance.setRevision(revisionVersion);
        return instance;
    }

    @Override
    public int compareTo(Version target) {
        if (target == null) {
            return 1;
        }
        int targetMajor = target.getMajor();
        int targetMinor = target.getMinor();
        int targetRevision = target.getRevision();
        if (major == targetMajor) {
            if (minor == targetMinor) {
                return Integer.compare(revision, targetRevision);
            } else {
                return Integer.compare(minor, targetMinor);
            }
        } else {
            return Integer.compare(major, targetMajor);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }

        Version version = (Version) o;
        return version.major == this.major && version.minor == this.minor && version.revision == this.revision;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = HASHCODE_BASE * result + minor;
        result = HASHCODE_BASE * result + revision;
        return result;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return major+"."+minor+"."+revision;
    }
}
