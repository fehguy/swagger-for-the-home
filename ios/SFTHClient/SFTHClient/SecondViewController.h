#import <UIKit/UIKit.h>
#import "SWGPhidgetApi.h"

@interface SecondViewController : UIViewController {
    UISwitch* switch1;
    UISwitch* switch2;
    UISwitch* switch3;
    UISwitch* switch4;
    UISwitch* switch5;
    UISwitch* switch6;
    UISwitch* switch7;
    UISwitch* switch8;
    UISwitch* switch9;
    UISwitch* switch10;

    
    UILabel* label1;
    UILabel* label2;
    UILabel* label3;
    UILabel* label4;
    UILabel* label5;
    UILabel* label6;
    UILabel* label7;
    UILabel* label8;
    UILabel* label9;
    UILabel* label10;
}

-(IBAction)switch1ValueChanged:(id)sender;
-(IBAction)switch1Timer:(id)sender;

-(IBAction)switch2ValueChanged:(id)sender;
-(IBAction)switch2Timer:(id)sender;

-(IBAction)switch3ValueChanged:(id)sender;
-(IBAction)switch3Timer:(id)sender;

-(IBAction)switch4ValueChanged:(id)sender;
-(IBAction)switch4Timer:(id)sender;

-(IBAction)switch5ValueChanged:(id)sender;
-(IBAction)switch5Timer:(id)sender;

-(IBAction)switch6ValueChanged:(id)sender;
-(IBAction)switch6Timer:(id)sender;

-(IBAction)switch7ValueChanged:(id)sender;
-(IBAction)switch7Timer:(id)sender;

-(IBAction)switch8ValueChanged:(id)sender;
-(IBAction)switch8Timer:(id)sender;

-(IBAction)switch9ValueChanged:(id)sender;
-(IBAction)switch9Timer:(id)sender;

-(IBAction)switch10ValueChanged:(id)sender;
-(IBAction)switch10Timer:(id)sender;

-(IBAction)refreshState;

@property (nonatomic, retain) IBOutlet UISwitch* switch1;
@property (nonatomic, retain) IBOutlet UISwitch* switch2;
@property (nonatomic, retain) IBOutlet UISwitch* switch3;
@property (nonatomic, retain) IBOutlet UISwitch* switch4;
@property (nonatomic, retain) IBOutlet UISwitch* switch5;
@property (nonatomic, retain) IBOutlet UISwitch* switch6;
@property (nonatomic, retain) IBOutlet UISwitch* switch7;
@property (nonatomic, retain) IBOutlet UISwitch* switch8;
@property (nonatomic, retain) IBOutlet UISwitch* switch9;
@property (nonatomic, retain) IBOutlet UISwitch* switch10;


@property (nonatomic, retain) IBOutlet UILabel* label1;
@property (nonatomic, retain) IBOutlet UILabel* label2;
@property (nonatomic, retain) IBOutlet UILabel* label3;
@property (nonatomic, retain) IBOutlet UILabel* label4;
@property (nonatomic, retain) IBOutlet UILabel* label5;
@property (nonatomic, retain) IBOutlet UILabel* label6;
@property (nonatomic, retain) IBOutlet UILabel* label7;
@property (nonatomic, retain) IBOutlet UILabel* label8;
@property (nonatomic, retain) IBOutlet UILabel* label9;
@property (nonatomic, retain) IBOutlet UILabel* label10;

@end
