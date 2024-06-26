package onetoone.Clans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Clans.ClanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer clan_xp;
    private Integer clan_leader;
    private String clan_name;

    private String clan_type;

    private String clan_members;

    private int max_members = 10;

    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL)
    private List<User> members;


    public Clan(String clan_name, int leader_id, UserRepository userRepository) {
            this.clan_name = clan_name;
            this.clan_leader = leader_id;
            this.clan_xp = 0;
            ArrayList<Integer> mems = new ArrayList<Integer>();
            mems.add(leader_id);
            setMember(mems);
            this.clan_type = "Open";
    }

    public void setMember(ArrayList<Integer> members){
        ObjectMapper objectMapper = new ObjectMapper();
        this.clan_members = members.toString();
    }

    public ArrayList<Integer> toIntList(String clan_members) {
        int count = 1;
        ArrayList<Integer> ret_list = new ArrayList<Integer>();
        while (clan_members.charAt(count) != ']') {
            String l = "";
            while (clan_members.charAt(count) != ',' && clan_members.charAt(count) != ']') {
                if(clan_members.charAt(count) == ' ') {
                    count++;
                }
                else {
                    l += clan_members.charAt(count);
                    count++;
                }
            }
            ret_list.add(Integer.parseInt(l));
            if(clan_members.charAt(count) != ']') {
                count++;
            }
            l = "";
        }
        return ret_list;
    }

    public Clan() {}

    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getLeader() {
        return clan_leader;
    }

    public String getClanName() {
        return clan_name;
    }

    public String getClanType() {
        return this.clan_type;
    }

    public void setClanType(String s) {
        this.clan_type = s;
    }

    public int getClan_xp() {
        return clan_xp;
    }

    public void setClan_xp(int cxp) {
        this.clan_xp = cxp;
    }
    public String getMembers() {
        return clan_members;
    }

    public void setMax_members(int max) {
        this.max_members = max;
    }

}
