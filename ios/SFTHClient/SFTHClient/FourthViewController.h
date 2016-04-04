//
//  FourthViewController.h
//  SFTHClient
//
//  Created by Tony Tam on 11/19/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FourthViewController : UIViewController {
    bool connected;

    UIButton* logoButton;
    UISwitch* muteSwitch;
}

-(IBAction)refreshState;
-(IBAction)muteMusic:(id)sender;

@property (nonatomic, retain) IBOutlet UIButton* logoButton;

@property (nonatomic, retain) IBOutlet UISwitch* muteSwitch;

@end
