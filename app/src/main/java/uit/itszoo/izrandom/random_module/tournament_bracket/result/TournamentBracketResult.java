package uit.itszoo.izrandom.random_module.tournament_bracket.result;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.ventura.bracketslib.BracketsView;
import com.ventura.bracketslib.model.ColomnData;
import com.ventura.bracketslib.model.CompetitorData;
import com.ventura.bracketslib.model.MatchData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.tournament_bracket.TournamentBracketActivity;

public class TournamentBracketResult extends FragmentActivity {
    ArrayList<String> participants = new ArrayList<>();
    BracketsView bracketsView;
    MaterialButton backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_bracket_result);
        participants = getIntent().getStringArrayListExtra(TournamentBracketActivity.LIST_PARTICIPANT);
        bracketsView = findViewById(R.id.bracket_view);
        initMatch();
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );
    }
    void initMatch()
    {
        int numMatch = (int) (Math.log(participants.size())/Math.log(2));
        int subMatch = (int) (participants.size() - Math.pow(2, numMatch));
        int indexOfParticipant = 0;
        ArrayList<String> copyParticipants = new ArrayList<>(participants);
        Collections.shuffle(copyParticipants);
        ArrayList<CompetitorData> listCompetitorData = new ArrayList<>();
        ArrayList<ArrayList<MatchData>> listMatchDataOfTournament = new ArrayList<>();
        for(int i = 0 ; i < participants.size();i++)
        {
            listCompetitorData.add(new CompetitorData(copyParticipants.get(i), "-"));
        }
        int numMatchQualifier1 = numMatch*2;
        List<Integer> indexOfRealMatchQualifier1 = new ArrayList<>();

        int period = subMatch == 0?0:(int)numMatchQualifier1/subMatch;
        listMatchDataOfTournament.add(new ArrayList<>());
        if(period != 0)
        {
            for(int i = 0; i< numMatchQualifier1 ; i ++)
            {
                if(i%period == 0)
                {
                    listMatchDataOfTournament.get(0).add(
                            new MatchData(listCompetitorData.get(indexOfParticipant),
                                    listCompetitorData.get(indexOfParticipant+1)));
                    indexOfParticipant+=2;
                    indexOfRealMatchQualifier1.add(i);
                }
                else {
                    listMatchDataOfTournament.get(0).add(
                            new MatchData(new CompetitorData("", "-"),
                                    new CompetitorData("", "-"))
                    );
                }
                System.out.println("Oke la");
            }
        }
        else{
            for(int i = 0; i< copyParticipants.size()/2; i++)
            {
                listMatchDataOfTournament.get(0).add(
                        new MatchData(listCompetitorData.get(indexOfParticipant),
                                listCompetitorData.get(indexOfParticipant+1)));
                indexOfParticipant+=2;
                System.out.println("Oke");
            }
        }
        ArrayList<ColomnData> listColumnData = new ArrayList<>();
        listColumnData.add(new ColomnData(listMatchDataOfTournament.get(0)));
        bracketsView.setBracketsData(listColumnData);
    }
}
