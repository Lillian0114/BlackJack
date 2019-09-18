public class Player_B0544203 extends Player {
	int Soft , Hard;
	int lastChips;
	int round=1;
	int big=0; //大牌
	int small=0; //小牌
	int mid=0;
	int loseCount;
	public Player_B0544203(int chips) {
		super("石家安(B0544203)", chips); 
	}

	public int make_bet() {
		// TODO Auto-generated method stub
		int new_bet=get_current_chips()/10;
		if(lastChips>get_current_chips()){
			loseCount++;
			if(loseCount==1){
				setBet(new_bet*2);
			}
			else{
				loseCount=0;
				round=1;
			}
		}
		else if(lastChips==get_current_chips()){
			round--;
		}
		
		if(round%3==1){
			setBet(new_bet);
			round++;
		}
		else if(round%3==2){
			new_bet*=2;
			setBet(new_bet);
			round++;
		}
		else if(round%3==0){
			new_bet*=3;
			setBet(new_bet);
			round++;
		}
	
		lastChips=get_current_chips();

		if(getBet() <= get_current_chips()) {
			return getBet();
		} 
		else {
			setBet(0);
			return getBet();
		}
	}
	@Override
	
	public int getTotalValue() {
		int Ace_count = 0;
		int total_value = 0;
		Soft=0;
		Hard=0;
		for (Card c : this.getOneRoundCard()) {
			if (Ace_count == 0 && c.getRank() == 1) {
				Ace_count = 1;
				continue;
			} else {
				if (c.getRank() == 11 || c.getRank() == 12 || c.getRank() == 13)
					total_value += 10;
				else
					total_value += c.getRank();
			}
		}
		if (Ace_count != 0) {
			if (total_value < 11) {
				total_value += 11;
				Soft++;
			} else {
				total_value += 1;
				Hard++;
			}

		}
		return total_value;
	}

	
	@Override
	public boolean hit_me(Table table) {
		//int[] suit={64,64,64,64,64,64,64,64,64,64,64,64,64};
		small=0; big=0;mid=0;
		for(Card c: table.getOpenedCards()){
			if(c.getRank()>=2&&c.getRank()<=6){
				small++;
			}
			else if(c.getRank()>=10 || c.getRank()==1){
				big++;
			}
		}
		
		boolean ans=true;
		int totalValue = getTotalValue();
		int openCard=table.get_face_up_card_of_dealer().getRank();
		if(Hard!=0){
			if((totalValue==16 || totalValue==15) && ((openCard==1 || openCard>=6) || big>=50)){
				ans=true;
			}
			else if(totalValue<=14){
				ans=true;
			}
			else{
				ans=false;
			}
		}
		else if(Hard==0 && Soft!=0){
			if(totalValue<=17){
				ans=true;
			}
			else if(totalValue==18 && (openCard>=9 && openCard<=13)){
				ans=true;
			}
			else if(totalValue==18 && openCard<=6){
				ans=true;
			}
			else{
				ans=false; 
			}
		}
		else{
			if (totalValue >= 17) {
				ans= false;
			} else {
				ans= true;
			}
		}
		return ans;
	}
	
}		
