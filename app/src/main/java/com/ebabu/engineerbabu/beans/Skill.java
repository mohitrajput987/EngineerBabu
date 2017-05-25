package com.ebabu.engineerbabu.beans;

/**
 * Created by hp on 13/05/2017.
 */
public class Skill {
    private int skill_id;
    private String skill_name;
    private boolean isChecked;

    public Skill(int skill_id, String skill_name) {
        this.skill_id = skill_id;
        this.skill_name = skill_name;
    }

    public int getId() {
        return skill_id;
    }

    public void setId(int skill_id) {
        this.skill_id = skill_id;
    }

    public String getName() {
        return skill_name;
    }

    public void setName(String skill_name) {
        this.skill_name = skill_name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public int hashCode() {
        return skill_id;
    }

    @Override
    public boolean equals(Object obj) {
        Skill skill = (Skill) obj;
        if (this.skill_id == skill.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skill_id=" + skill_id +
                ", name='" + skill_name + '\'' +
                '}';
    }
}
