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

@end

